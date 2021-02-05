package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schema.Element
import schemanew.Namespace

object InterfaceGenerator: Generator {

    fun generate(namespace: Element.Model.Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder(namespace.name).apply {
            namespace.types.forEach { type ->
                if (type.type == null)
                    addType(
                        TypeSpec.interfaceBuilder(type.name).apply {
                            type.slots.forEach { slot ->
                                addProperty(
                                    slot.asPropertySpec(false).build()
                                )

                            }
                        }.build()
                    )
            }
        }
        val file = FileSpec.builder("generated.model", namespace.name).apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

}

object InterfaceGenerator2: Generator {

    fun generate(namespace: Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder("${namespace.name}2").apply {
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
        val file = FileSpec.builder("generated.model", "${namespace.name}2").apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

}
