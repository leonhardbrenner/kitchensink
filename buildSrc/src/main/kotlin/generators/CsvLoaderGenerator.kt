package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element

//TODO - try to make this go away. Seed not on why we deleted Serializable below.
object Dto2Generator: Generator {
    fun generateDto(namespace: Element.Model.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto2")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto2").apply {
                    namespace.types.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            //This is all that is different between Dto and Dto2 for what ever reason @Serializable polutes the data class with seen1 and serializerConstructor:(
                            //.addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
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
}
