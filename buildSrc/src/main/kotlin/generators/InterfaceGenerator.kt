package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schema.IElement

class Model(element: IElement): IElement by element {
    val namespaces by lazy {
        children.map { Namespace(it)}
    }
    inner class Namespace(element: IElement): IElement by element {
        val types by lazy {
            children.map { Type(it) }
        }
    }
    inner class Type(element: IElement): IElement by element {
        val slots by lazy {
            children.map { Slot(it) }
        }
        val packageName: String
            get() = parent?.path?.substring(1)?.replace("/", ".")?:""
    }
    inner class Slot(element: IElement): IElement by element
}

object InterfaceGenerator: Generator {

    fun generate(manifest: IElement) {
        val model = Model(manifest)
        model.namespaces.forEach { namespace ->
            val typeSpec = TypeSpec.interfaceBuilder(namespace.name).apply {
                namespace.types.forEach { element ->
                    if (element.type == null)
                        addType(
                            TypeSpec.interfaceBuilder(element.name).apply {
                                element.slots.forEach { child ->
                                    addProperty(
                                        child.asPropertySpec(false).build()
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
