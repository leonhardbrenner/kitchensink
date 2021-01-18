package generators

import JohnnySeeds
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import java.io.StringWriter
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

class ComplexType <T: Any> (val manifest: KClass<T>) {
    val description = manifest.simpleName + " - " + manifest.qualifiedName
    val complexTypes
    get() = manifest.nestedClasses.map { ComplexType(it) }
    val simpleTypes
        get() = manifest.members
            .filterNot {
                listOf("equals", "hashCode", "toString").contains(it.name)
            }
            .map {
                SimpleType(it)
            }


}
class SimpleType (val manifest: KCallable<*>) {
    val description
    get() = "    ${manifest.name}, ${manifest.returnType.asTypeName()}"
}


fun main(args: Array<String>) {
    fun test(x: JohnnySeeds.DetailedSeed) = x.hashCode()

    val reflector = ComplexType(JohnnySeeds::class)
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
                reflector.complexTypes.forEach {
                    Class(it.manifest, modifiers = listOf(KModifier.DATA)) {

                    }
                }
            }

            Function("main") {
                Parameter("args", String::class) {
                    addModifiers(KModifier.VARARG)
                }

                Body {
                    addStatement("%T(args[0]).greet()", ClassName("", "Greeter"))
                }
            }

        }.build().writeTo(this)
    }.toString()
    println(generatedCode)
}