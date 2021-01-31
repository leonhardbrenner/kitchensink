package generators

import com.squareup.kotlinpoet.*
import schema.Workflow
import java.io.File

class GenerateWorkflows(val workflows_: List<Workflow>): Generator {

    fun generate() {
        val workflowsBuilder = TypeSpec.objectBuilder("Workflows")
        workflows_.forEach { workflow ->
            generate(workflowsBuilder, workflow)
        }
        val fsb = FileSpec.builder("generated.workflows", "Workflows")
            .addImport("java.lang.reflect", "Field")
        fsb.addType(workflowsBuilder.build())
        fsb.build().writeTo(File("$path/commonMain/kotlin"))
    }

    fun generate(workflowsBuilder: TypeSpec.Builder, workflow: Workflow) {
        val workflowBuilder = TypeSpec.objectBuilder(workflow.name_)
        workflowBuilder.addFunction(
            FunSpec.builder("init").addCode("return Forms.%L()\n", workflow.init_).build()
        )
        workflowBuilder.addType(
            TypeSpec.interfaceBuilder("${workflow.name_}Event").apply {
                addFunction(
                    FunSpec.builder("execute").apply {
                        addParameter(
                            ParameterSpec.builder(
                                "event", ClassName("workflows", "Event")
                                    .copy(nullable = true)
                            ).build()
                        )
                        returns(
                            ClassName("workflows", "Workflow")
                                .copy(nullable = true)
                        )
                        addModifiers(KModifier.ABSTRACT)
                    }.build()
                )
            }.build()
        )
        val formBuilder = TypeSpec.classBuilder("Forms")
        workflow.states_.forEach { state ->
            generateState(formBuilder, workflow, state)
        }
        workflowBuilder.addType(formBuilder.build())
        workflowsBuilder.addType(workflowBuilder.build())
    }

    fun generateState(formBuilder: TypeSpec.Builder, workflow: Workflow, state: Workflow.State) {
        val stateBuilder = TypeSpec.classBuilder(state.name_)
            .superclass(ClassName("workflows", "Workflow"))
            .addSuperinterface(ClassName("", "${workflow.name_}Event"))
        stateBuilder.addType(
            TypeSpec.interfaceBuilder("${state.name_}Event").apply {
                addFunction(
                    FunSpec.builder("execute").apply {
                        addParameter(
                            ParameterSpec.builder(
                                "workflow", ClassName("", state.name_)
                                    .copy(nullable = true)
                            ).build()
                        )
                        returns(
                            ClassName("workflows", "Workflow")
                                .copy(nullable = true)
                        )
                        addModifiers(KModifier.ABSTRACT)
                    }.build()
                )
            }.build()
        )
        stateBuilder.addFunction(
            FunSpec.builder("execute").apply {
                addModifiers(KModifier.OVERRIDE)
                addParameter(
                    ParameterSpec.builder(
                        "event", ClassName("workflows", "Event")
                            .copy(nullable = true)
                    ).build()
                )
                returns(
                    ClassName("workflows", "Workflow")
                        .copy(nullable = true)
                )
                addCode("return (event as ${state.name_}Event?)!!.execute(this)")
            }.build()
        )
        state.labels.forEach { label ->
            generateLabel(stateBuilder, workflow, state, label)
        }
        formBuilder.addType(stateBuilder.build())
    }

    fun generateLabel(stateBuilder: TypeSpec.Builder, workflow: Workflow, state: Workflow.State, label: Workflow.State.Label) {
        stateBuilder.addType(
            TypeSpec.interfaceBuilder("I${label.name_}").apply {
                addFunction(
                    FunSpec.builder("execute").apply {
                        addParameter(
                            ParameterSpec.builder(
                                "state", ClassName("", state.name_)
                                    .copy(nullable = true)
                            ).build()
                        )
                        addParameter(
                            ParameterSpec.builder(
                                "event", ClassName("", label.name_)
                                    .copy(nullable = true)
                            ).build()
                        )
                        returns(
                            ClassName("workflows", "Workflow")
                                .copy(nullable = true)
                        )
                        addModifiers(KModifier.ABSTRACT)
                    }.build()
                )
            }.build()
        )
        val labelBuilder = TypeSpec.classBuilder("${label.name_}").apply {
            superclass(ClassName("workflows", "Event"))
            addSuperinterface(ClassName("", "${state.name_.toString()}Event"))
        }
        if (label.transitions.size > 0) {
            labelBuilder.addType(
                TypeSpec.classBuilder("DefaultImpl")
                    .addSuperinterface(ClassName("", "I${label.name_.toString()}")).apply {
                        addFunction(
                            FunSpec.builder("execute").apply {
                                addParameter(
                                    ParameterSpec.builder(
                                        "state", ClassName("", state.name_)
                                            .copy(nullable = true)
                                    ).build()
                                )
                                addParameter(
                                    ParameterSpec.builder(
                                        "event", ClassName("", label.name_)
                                            .copy(nullable = true)
                                    ).build()
                                )
                                returns(
                                    ClassName("workflows", "Workflow")
                                        .copy(nullable = true)
                                )
                                addModifiers(KModifier.OVERRIDE)
                                //XXX - I don't believe this is correct. Why are we not expanding all transitions.
                                addCode("return ${label.transitions.first.to_}")
                            }.build()
                        )
                    }
                    .addType(TypeSpec.companionObjectBuilder().apply {
                        addProperty(PropertySpec.builder(
                            "singleton__",
                            ClassName("", "I${label.name_}")
                        )
                            .initializer("DefaultImpl()")
                            .build()
                        )
                        //                            val singleton__: IACTIVATE = DefaultImpl()
                    }.build()).build()
            )
        }
        labelBuilder.addFunction(
            FunSpec.builder("execute")
                .addParameter(
                    ParameterSpec("workflow",
                        ClassName("", state.name_).copy(nullable = false))
                )
                .returns(ClassName("workflows", "Workflow"))
                .addCode("return rule__.execute(workflow, this)")
                .build()
        )
        labelBuilder.addType(
            TypeSpec.companionObjectBuilder()
                .addProperty(
                    PropertySpec.builder(
                        "rule__",
                        ClassName("", "I${label.name_}")
                    )
                        .initializer("DefaultImpl.singleton__")
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(
                        "singleton__",
                        ClassName("", "I${label.name_}")
                    )
                        .initializer("%L()", label.name_)
                        .build()
                )
            .build())
        stateBuilder.addType(labelBuilder.build())
    }
}

fun generateWorkflows() {
    val g = GenerateWorkflows(
        listOf(workflows.printer)
    )
    g.generate()
}
