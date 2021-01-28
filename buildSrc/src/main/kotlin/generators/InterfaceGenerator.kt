package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import java.io.File
import schema.Element

object InterfaceGenerator: Generator {
    fun generate(manifest: Element) {
        val file = FileSpec.builder("generated.model", "JohnnySeeds").apply {
            addType(
                TypeSpec.interfaceBuilder("JohnnySeeds").apply {
                    manifest.children.forEach { element ->
                        if (element.type==null)
                            addType(
                                TypeSpec.interfaceBuilder(element.name).apply {
                                    element.children.forEach { child ->
                                        addProperty(
                                            PropertySpec.builder(
                                                child.name,
                                                child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable)
                                            ).mutable(false).build()
                                        )

                                    }
                                }.build())
                    }
                }.build()
            )
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }
}
