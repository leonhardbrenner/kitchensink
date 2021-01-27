package generators

import JohnnySeeds
import com.squareup.kotlinpoet.*
import java.io.Serializable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

class Container <T: Any> (val source: KClass<T>) {

    val containers
    get() = source.nestedClasses.map { Container(it) }

    val elements
    get() = source.members
            .filterNot { ElementX.filters.contains(it.name) }
            .map { ElementX(it) }

}

class ElementX (val source: KCallable<*>) {

    companion object {
        //TODO - Investigate why these are polluting our namespace. I did not think interfaces would have these.
        val filters = listOf("equals", "hashCode", "toString").toSet()
    }

    val name
    get() = source.name

    val returnType
    get() = source.returnType

}


fun main(args: Array<String>) {
    val reflector = Container(JohnnySeeds::class)
    val file = FileSpec.builder("", "HelloWorld")
            .addType(
                    TypeSpec.interfaceBuilder("JohnnySeedsDto").apply {
                        reflector.containers.forEach { container ->
                            val typeSpec = TypeSpec.classBuilder(container.source.simpleName!!)
                                    .addAnnotation(Serializable::class)
                                    .addModifiers(KModifier.DATA)
                                    .addSuperinterface(container.source)
                                    .primaryConstructor(
                                            FunSpec.constructorBuilder().apply {
                                                container.elements.forEach { element ->
                                                    addParameter(element.name, element.returnType.asTypeName()).build()
                                                }
                                            }.build()
                                    )
                                    .apply {
                                        container.elements.forEach { element ->
                                            val propertySpec = PropertySpec.builder(element.name, element.returnType.asTypeName(), KModifier.FINAL)
                                                    .initializer(element.name)
                                                    //.mutable(true)
                                                    .build()
                                            addProperty(propertySpec)
                                        }
                                    }
                                    .addFunction(
                                            FunSpec.builder("create")
                                                    .addParameter("source", container.source.asTypeName())
                                                    //Look at CodeBlock.addArgument and you will see L stands for literal
                                                    .addCode("return %L(%L)", container.source.simpleName!!, container.elements.map { "source.${it.name}" }.joinToString(", "))
                                                    .build()
                                    )
                                    .addType(
                                            TypeSpec.companionObjectBuilder().apply {
                                                val propertySpec = PropertySpec.builder("path", String::class, KModifier.FINAL)
                                                        .initializer("\"/${container.source.qualifiedName!!.replace('.', '/')}\"")
                                                        //.mutable(true)
                                                        .build()
                                                addProperty(propertySpec)
                                            }.build()
                                    )
                                    .build()
                            addType(typeSpec)
                        }
                    }.build()
            ).build()

    file.writeTo(System.out)
}