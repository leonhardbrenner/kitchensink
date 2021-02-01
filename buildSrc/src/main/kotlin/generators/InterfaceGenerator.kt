package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schema.Element

object InterfaceGenerator: Generator {

    fun generate(model: Element.Model) {
        model.namespaces.forEach { namespace ->
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
            val file = FileSpec.builder("generated.model", "JohnnySeeds").apply {
                addType(typeSpec.build())
            }.build()
            val writer = File("$path/commonMain/kotlin")
            file.writeTo(writer)
        }
    }
}
