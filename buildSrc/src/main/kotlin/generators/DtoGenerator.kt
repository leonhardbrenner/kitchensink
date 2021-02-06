package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element
import schemanew.Namespace

object DtoGenerator: Generator {

    fun generate(namespace: Element.Model.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto").apply {
                    namespace.types.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(type.packageName, type.name))
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    type.slots.forEach { slot ->
                                        addParameter(
                                            slot.name,
                                            slot.type!!.asTypeName().copy(nullable = slot.nullable)
                                        ).build()
                                    }
                                }.build()
                            )
                            .apply {
                                type.slots.forEach { slot ->
                                    addProperty(
                                        //TODO - make extension function
                                        slot.asPropertySpec(false, KModifier.OVERRIDE)
                                            .initializer(slot.name).build()
                                    )
                                }
                            }
                            .addType(
                                TypeSpec.companionObjectBuilder().apply {
                                    val propertySpec = PropertySpec.builder("path", String::class, KModifier.FINAL)
                                        .initializer("\"${type.path}\"")
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                    addProperty(
                                        PropertySpec.builder("header", String::class, KModifier.FINAL)
                                            .initializer("%S\n", type.slots.map { it.columnName }.joinToString("\t"))
                                            .build()
                                    )
                                    addFunction(
                                        FunSpec.builder("create")
                                            .addParameter("source", ClassName(type.packageName, type.name))
                                            //Look at CodeBlock.addArgument and you will see L stands for literal
                                            .addCode(
                                                "return %L(%L)",
                                                type.name!!,
                                                type.children.map { "source.${it.name}" }.joinToString(", ")
                                            )
                                            .build()
                                    )
                                }.build()
                            )
                            .build()
                        addType(typeSpec)
                    }
                }.build()
            ).build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

    fun generate2(namespace: Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto2")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto2").apply {
                    namespace.complexTypes.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(type.packageName, type.name))
                            //.primaryConstructor(
                            //    FunSpec.constructorBuilder().apply {
                            //        type.elements.forEach { element ->
                            //            with (element) {
                            //                addParameter(
                            //                    name,
                            //                    type!!.asTypeName().copy(nullable = type.nullable)
                            //                ).build()
                            //            }
                            //        }
                            //    }.build()
                            //)
                            .apply {
                                type.elements.forEach { element ->
                                    addProperty(
                                        //TODO - make extension function
                                        element.asPropertySpec(false, KModifier.OVERRIDE)
                                            .initializer(element.name).build()
                                    )
                                }
                            }
                            .addType(
                                TypeSpec.companionObjectBuilder().apply {
                                    val propertySpec = PropertySpec.builder("path", String::class, KModifier.FINAL)
                                        .initializer("\"${type.path}\"")
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                    //addProperty(
                                    //    PropertySpec.builder("header", String::class, KModifier.FINAL)
                                    //        .initializer("%S\n", type.slots.map { it.columnName }.joinToString("\t"))
                                    //        .build()
                                    //)
                                    addFunction(
                                        FunSpec.builder("create")
                                            .addParameter("source", ClassName(type.packageName, type.name))
                                            //Look at CodeBlock.addArgument and you will see L stands for literal
                                            .addCode(
                                                "return %L(%L)",
                                                type.name!!,
                                                type.elements.map { "source.${it.name}" }.joinToString(", ")
                                            )
                                            .build()
                                    )
                                }.build()
                            )
                            .build()
                        addType(typeSpec)
                    }
                }.build()
            ).build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }
}
