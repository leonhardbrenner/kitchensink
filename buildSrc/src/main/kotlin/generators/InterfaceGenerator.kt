package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schemanew.Namespace
import schema.ManifestNew

object InterfaceGenerator: Generator {

    fun generate(namespace: Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder("${namespace.name}").apply {
            namespace.complexTypes.forEach { type ->
                    addType(
                        TypeSpec.interfaceBuilder(type.name).apply {
                            type.elements.forEach { element ->
                                addProperty(
                                    element.asPropertySpec(false).build()
                                )

                            }
                        }.build()
                    )
            }
        }
        val file = FileSpec.builder("generated.model", "${namespace.name}").apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

}

object InterfaceGenerator2: Generator {

    fun generate(namespace: ManifestNew.Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder("${namespace.name}").apply {
            namespace.types.forEach { type ->
                generateType(type)
            }
        }
        val file = FileSpec.builder("generated.model", "${namespace.name}").apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

    fun TypeSpec.Builder.generateType(type: ManifestNew.Namespace.Type): TypeSpec.Builder = addType(
        TypeSpec.interfaceBuilder(type.name).apply {
            type.elements.forEach { element ->
                addProperty(
                    PropertySpec.builder(
                        element.name,
                        element.type.typeName
                    )
                        .addModifiers(modifiers.toList() )
                        .mutable(false)
                        .build()
                )
            }
            type.types.forEach { type ->
                generateType(type)
            }
        }.build().apply { println("${type.name} created") }
    )
}
