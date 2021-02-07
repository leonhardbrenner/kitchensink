package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schemanew.Namespace

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
