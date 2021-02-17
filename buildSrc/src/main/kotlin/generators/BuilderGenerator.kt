package generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

import java.io.File
import schema.Manifest

object BuilderGenerator: Generator {

    override fun generate(namespace: Manifest.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Builder")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Builder").apply {
                    namespace.types.forEach { type ->
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
