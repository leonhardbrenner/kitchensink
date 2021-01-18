package generators

import JohnnySeeds
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
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
    reflector.complexTypes.forEach { x ->
        println(x.description)
        x.simpleTypes.forEach { y->
            //TODO - investigate I find it odd that the outer interface does not have these.
            println(y.description)
        }
    }
    reflector.simpleTypes.forEach { y->
        //TODO - investigate I find it odd that the outer interface does not have these.
        println(y.description)
    }

    val generatedCode = StringWriter().apply {
        val rootName = "JohnnySeedsDto"
        FileBuilder("this.is.my.package", rootName) {

            Interface(rootName) {
                reflector.complexTypes.forEach { x ->
                    Class(x.source, modifiers = listOf(KModifier.DATA)) {
                        CompanionObject {
                            Property("path", String::class.createType()) {
                                initializer("/johnnySeeds/*")
                            }
                        }

                        PrimaryConstructor {
                            x.simpleTypes.forEach { element ->
                                Parameter(element.source.name, element.source.returnType) {

                                }
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