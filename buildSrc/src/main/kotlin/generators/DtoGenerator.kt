package generators

import com.squareup.kotlinpoet.*
import generators.InterfaceGenerator2.generateType
import schema.ManifestNew
import java.io.File
import schemanew.Namespace

object DtoGenerator: Generator {

    fun generate(namespace: Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto").apply {
                    namespace.complexTypes.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(type.packageName, type.name))
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    type.elements.forEach { element ->
                                        addParameter(element.name, element.type.typeName).build()
                                    }
                                }.build()
                            )
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
                                    val propertySpec = PropertySpec.builder("path", String::class)
                                        .initializer("\"${type.path}\"")
                                        .build()
                                    addProperty(propertySpec)
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

object DtoGenerator2: Generator {
    //TODO: Generate the top level elements
    fun generate(namespace: ManifestNew.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto").apply {
                    namespace.types.forEach { generateType(it) }
                }.build()
            ).build()
        //val writer = File("$path/commonMain/kotlin")
        //file.writeTo(writer)
        file.writeTo(System.out)
    }

    fun TypeSpec.Builder.generateType(type: ManifestNew.Namespace.Type): TypeSpec.Builder = addType(
        TypeSpec.classBuilder(type.name)
            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
            .addModifiers(KModifier.DATA)
            .addSuperinterface(ClassName("generated.model", type.dotPath()))
            .primaryConstructor(
                FunSpec.constructorBuilder().apply {
                    type.elements.forEach { element ->
                        addParameter(element.name, element.type.className).build()
                    }
                }.build()
            )
            .apply {
                type.elements.forEach { element ->
                    val classname = ClassName("generated.model", element.type.rawType.toString().replace("?", ""))
                        .copy(nullable = element.type.nullable)
                    addProperty(
                        PropertySpec.builder(
                            element.name,
                            classname
                        )
                            .addModifiers(listOf(KModifier.OVERRIDE))
                            .mutable(false)
                            .initializer(element.name)
                            .build()
                    )
                }
            }
            .addType(
                TypeSpec.companionObjectBuilder().apply {
                    val propertySpec = PropertySpec.builder("path", String::class)
                        .initializer("\"${type.path}\"")
                        .build()
                    addProperty(propertySpec)
                    addFunction(
                        FunSpec.builder("create")
                            .addParameter("source", ClassName("generated.model", type.dotPath()))
                            //Look at CodeBlock.addArgument and you will see L stands for literal
                            .addCode(
                                "return %T(%L)",
                                ClassName("generated.model", type.dotPath("Dto")),
                                type.elements.map { "source.${it.name}" }.joinToString(", ")
                            )
                            .build()
                    )
                }.build()
            ).apply {
                type.types.forEach {
                    generateType(it)
                }
            }.build()
    )
}
