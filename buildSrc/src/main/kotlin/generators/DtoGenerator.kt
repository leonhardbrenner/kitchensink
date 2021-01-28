package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element

object DtoGenerator: Generator {
    fun generateDto(manifest: Element) {
        //TODO - consider dropping the generated. prefix. We can keep the files in a different directoru from packagename???
        val file = FileSpec.builder("generated.model", "JohnnySeedsDto")
            .addType(
                TypeSpec.interfaceBuilder("JohnnySeedsDto").apply {
                    manifest.children.forEach { element ->
                        val typeSpec = TypeSpec.classBuilder(element.name)
                            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(element.packageName, element.name))
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    element.children.forEach { child ->
                                        addParameter(
                                            child.name,
                                            child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable)
                                        ).build()
                                    }
                                }.build()
                            )
                            .apply {
                                element.children.forEach { child ->
                                    val propertySpec = PropertySpec.builder(
                                        child.name,
                                        child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable),
                                        KModifier.OVERRIDE
                                    )
                                        .initializer(child.name)
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                }
                            }
                            .addType(
                                TypeSpec.companionObjectBuilder().apply {
                                    val propertySpec = PropertySpec.builder("path", String::class, KModifier.FINAL)
                                        .initializer("\"${element.path}\"")
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                    addFunction(
                                        FunSpec.builder("create")
                                            .addParameter("source", ClassName(element.packageName, element.name))
                                            //Look at CodeBlock.addArgument and you will see L stands for literal
                                            .addCode(
                                                "return %L(%L)",
                                                element.name!!,
                                                element.children.map { "source.${it.name}" }.joinToString(", ")
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
