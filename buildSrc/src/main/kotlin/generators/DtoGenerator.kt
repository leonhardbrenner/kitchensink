package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element

object DtoGenerator: Generator {
    fun generateDto(manifest: Element) {
        val model = Model(manifest)
        model.namespaces.forEach { namespace ->
            val file = FileSpec.builder("generated.model", "JohnnySeedsDto")
                .addType(
                    TypeSpec.interfaceBuilder("${namespace.name}Dto").apply {
                        namespace.types.forEach { type ->
                            val typeSpec = TypeSpec.classBuilder(type.name)
                                .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
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
                                        val propertySpec = PropertySpec.builder(
                                            slot.name,
                                            slot.type!!.asTypeName().copy(nullable = slot.nullable),
                                            KModifier.OVERRIDE
                                        )
                                            .initializer(slot.name)
                                            //.mutable(true)
                                            .build()
                                        addProperty(
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
                                        addFunction(
                                            FunSpec.builder("create")
                                                .addParameter("source", ClassName(type.packageName, type.name))
                                                //Look at CodeBlock.addArgument and you will see L stands for literal
                                                .addCode(
                                                    "return %L(%L)",
                                                    type.name!!,
                                                    type.children.map { "source.${it.name}" }.joinToString(", ")
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
}
