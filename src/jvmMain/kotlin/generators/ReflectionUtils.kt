package generators

import JohnnySeeds
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import java.io.Serializable
import java.io.StringWriter
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.createType

class Container <T: Any> (val source: KClass<T>) {
    val description = source.simpleName + " - " + source.qualifiedName

    val complexTypes
    get() = source.nestedClasses.map { Container(it) }

    val simpleTypes
    get() = source.members
            .filterNot { Element.filters.contains(it.name) }
            .map { Element(it) }
}

class Element (val source: KCallable<*>) {

    companion object {
        val filters = listOf("equals", "hashCode", "toString").toSet()
    }

    val description
    get() = "    ${source.name}, ${source.returnType.asTypeName()}"
}


fun main(args: Array<String>) {
    val reflector = Container(JohnnySeeds::class)
    val generatedCode = StringWriter().apply {
        val rootName = "JohnnySeedsDto"
        FileBuilder("", rootName) {

            Interface(rootName) {

                reflector.complexTypes.forEach { x ->
                    Class(x.source, modifiers = listOf(KModifier.DATA)) {
                        Annotation(Serializable::class) {

                        }

                        PrimaryConstructor {
                            x.simpleTypes.forEach { element ->
                                Property(element.source.name, element.source.returnType) {
                                    mutable(false)
                                }
                            }
                        }

                        CompanionObject {
                            Property("path", String::class.createType()) {
                                initializer("/johnnySeeds/*")
                            }
                        }
                    }
                }
            }

        }.build().writeTo(this)
    }.toString()

    println(generatedCode)
    val intType = Int::class.createType()
    val klass = intType.classifier
    println(klass!!.equals(Int::class))
}