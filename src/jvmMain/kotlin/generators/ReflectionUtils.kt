package generators

import JohnnySeeds
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

class ComplexType <T: Any> (private val manifest: KClass<T>) {
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
class SimpleType (private val manifest: KCallable<*>) {
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
}