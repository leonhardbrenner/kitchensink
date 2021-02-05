package schemanew

import java.util.*
import kotlin.reflect.KClass

object Manifest {
    val namespaces = HashMap<String, Namespace>()
    fun namespace(name: String, block: Namespace.() -> Unit) = Namespace(name, block).let { namespace ->
        namespaces[name] = namespace
    }
    init {
        namespace("builtIn") {
            //This deviates from XMLSchema which would make nullable an attribute of the type but in Kotlin we use String?
            SimpleType("string", String::class)
            SimpleType("nullableString", String::class, nullable = true)
            SimpleType("int", Int::class)
            SimpleType("nullableString", Int::class, nullable = true)
            SimpleType("long", Long::class)
            SimpleType("nullableLong", Long::class, nullable = true)
            SimpleType("boolean", Boolean::class)
            SimpleType("nullableBoolean", Boolean::class, nullable = true)
        }
    }
}

class Namespace(val name: String, block: Namespace.() -> Unit) {
    val types = HashMap<String, Type>()
    val simpleTypes = LinkedList<SimpleType>()
    val complexTypes = LinkedList<ComplexType>()
    val elements = LinkedList<Element>()

    fun complexType(name: String, block: ComplexType.() -> Unit) =
        ComplexType(name, block)


    fun element(name: String, type: String? = null, block: (ComplexType.() -> Unit)? = null) =
        Element(name, type, block)
    init { block() }
}

open class Element(val name: String, type: String?, val block: (ComplexType.() -> Unit)?) {
    val type: Type?
    val elements = LinkedList<Element>()
    init {
        val message = { "Element must have type xor block(which allows us to define one)" }
        this.type = if (type != null) {
            assert(block != null, message)
            val colonPosition = name.indexOf(':')
            Manifest.namespaces.get(name.substring(0, colonPosition))?.let { namespace ->
                //TODO - This should throw a name lookup exception.
                namespace.types.get(name.substring(colonPosition + 1))
            }
        } else {
            assert(block != null, message)
            ComplexType(name, block!!)
        }

    }
}

interface Type

open class ComplexType(val name: String, block: ComplexType.() -> Unit): Type {
    init {
        block()
    }
    val types = LinkedList<Type>()
    val elements = LinkedList<Element>()
    fun element(name: String, type: String? = null, block: (ComplexType.() -> Unit)? = null) =
        Element(name, type, block)

}

open class SimpleType(val name: String, kclass: KClass<*>?, nullable: Boolean = false): Type

fun main () {
    Manifest.namespace("BuckySoap") {
        element("ElementAndComplexType") {
            element("boolean", "builtin:boolean")
            element("int", "builtin:int")
            element("long", "builtin:long")
            element("string", "builtin:string")
        }
        complexType("ComplexType") {
            element("boolean", "builtin:boolean")
            element("int", "builtin:int")
            element("long", "builtin:long")
            element("string", "builtin:string")
        }
        element("element", "ComplexType")
    }
    Manifest.namespaces.values.forEach { namespace ->
        println(namespace.name)
    }
}