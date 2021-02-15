package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schemanew.Namespace

object BuilderGenerator: Generator {

    fun generate(namespace: Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Builder")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Builder").apply {
                    namespace.complexTypes.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    type.elements.forEach { element ->
                                        addParameter(element.name, element.type.typeName.copy(nullable = true)).build()
                                    }
                                }.build()
                            )
                            .apply {
                                type.elements.forEach { element ->
                                    addProperty(
                                        //TODO - make extension function
                                        PropertySpec.builder(
                                            element.name,
                                            element.type.typeName.copy(nullable = true)
                                        ).mutable(true)
                                            .initializer(element.name).build()
                                    )
                                }
                            }
                            .addFunction(
                                FunSpec.builder("build")
                                    .returns(ClassName("generated.model", "${type.namespace.name}.${type.name}"))
                                    .addCode(
                                        "return %L(\n%L\n)",
                                        "${type.namespace.name}Dto.${type.name}",
                                        type.elements.map {
                                            if (it.type.nullable)
                                                it.name
                                            else
                                                "${it.name} ?: throw IllegalArgumentException(\"${it.name} is not nullable\")"
                                        }.joinToString(",\n")
                                    )
                                    .build()
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
