package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element
import schemanew.Namespace

//TODO - try to make this go away. Seed not on why we deleted Serializable below.
object CsvLoaderGenerator: Generator {
    fun generate(namespace: Element.Model.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}CsvLoader")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}CsvLoader").apply {
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
                                                type.slots.map { "source.${it.name}" }.joinToString(", ")
                                            )
                                            .build()
                                    )
                                    addFunction(
                                        FunSpec.builder("loadCsv")
                                            .addParameter("pathname", String::class)
                                            .addCode("return model.loadCsv<${type.name}>(pathname, header)")
                                            .build()
                                    )
                                }.build()
                            )
                            .build()
                        addType(typeSpec)
                    }
                }.build()
            ).build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

    fun generate2(namespace: Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}CsvLoader2")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}CsvLoader2").apply {
                    namespace.complexTypes.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            //This is all that is different between Dto and Dto2 for what ever reason @Serializable polutes the data class with seen1 and serializerConstructor:(
                            //.addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(type.packageName, type.name))
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    type.elements.forEach { slot ->
                                        addParameter(
                                            slot.name,
                                            slot.type.typeName
                                        ).build()
                                    }
                                }.build()
                            )
                            .apply {
                                type.elements.forEach { slot ->
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
                                            .initializer("%S\n", type.elements.map { it.name }.joinToString("\t"))
                                            .build()
                                    )
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
                                    addFunction(
                                        FunSpec.builder("loadCsv")
                                            .addParameter("pathname", String::class)
                                            .addCode("return model.loadCsv<${type.name}>(pathname, header)")
                                            .build()
                                    )
                                }.build()
                            )
                            .build()
                        addType(typeSpec)
                    }
                }.build()
            ).build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

}
