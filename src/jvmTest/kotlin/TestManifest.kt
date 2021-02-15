import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties

import com.squareup.kotlinpoet.asTypeName

import org.junit.Test

const val UNBOUNDED = Int.MAX_VALUE

class Namespace(val kclass: KClass<*>) {
    val elements by lazy { kclass.memberProperties.map { Element(it) } }
    val types by lazy { kclass.nestedClasses.map { Type(it.createType(), it) } }
    class Element(val property: KProperty<*>) {
        val name get() = property.name
        val type = Type(property.returnType)
        val minOccurs = if (type.rawType.isMarkedNullable) 0 else 1
        val maxOccurs = if (property.returnType.classifier == List::class) UNBOUNDED else 1
    }
    class Type(
        val kType: KType, val kClass: KClass<*>? = null
    ) {
        val memberProperties = kClass?.memberProperties?:emptyList()
        val nestedClasses = kClass?.nestedClasses?:emptyList()
        val elements by lazy { memberProperties.map { Element(it) } }
        val types by lazy { nestedClasses.map { Type(it.createType(), it) } }
        val rawType = if (kType.classifier == List::class)
            kType.arguments[0].type!!
        else
            kType
        val typeName = rawType.asTypeName()
        val nullable = rawType.isMarkedNullable
    }
}

interface Manifest {
    val a: A
    val nullableA: A?
    val listOfA: List<A>
    val listOfNullableA: List<A?>
    val nullableListOfA: List<A>?
    val nullableListOfNullableA: List<A?>?
    interface A {
        val b: B
        val nullableB: B?
        val listOfB: List<B>
        val listOfNullableB: List<B?>
        val nullableListOfB: List<B>?
        val nullableListOfNullableB: List<B?>?
        interface B {
            val c: C
            val nullableC: C?
            val listOfC: List<C>
            val listOfNullableC: List<C?>
            val nullableListOfC: List<C>?
            val nullableListOfNullableC: List<C?>?
        }
    }
    interface C {
        val x: Int?
    }
}


fun output(namespace: Namespace) {
    namespace.elements.forEach {
        println("Element: ${it.name}, ${it.type.typeName} = minOccurs=${it.minOccurs} maxOccurs=${it.maxOccurs} raw=${it.type.rawType} nullable=${it.type.nullable}")
    }
    fun output(type: Namespace.Type, indent: String = "") {
        println("${indent}ComplexType: ${type.kType}")
        type.elements.forEach {
            println("\t${indent}Element: ${it.name}, ${it.type.typeName} = minOccurs=${it.minOccurs} maxOccurs=${it.maxOccurs} raw=${it.type.rawType} nullable=${it.type.nullable}")
        }
        type.types.forEach {
            output(it, "$indent\t")
        }
    }
    namespace.types.forEach {
        output(it)
    }
}

class TestManifest {
    @Test
    fun test() {
        output(Namespace(Manifest::class))
    }
}