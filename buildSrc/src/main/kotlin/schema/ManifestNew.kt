package schema

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties

const val UNBOUNDED = Int.MAX_VALUE

object ManifestNew {

    val namespaceMap = HashMap<String, Namespace>()

    val namespaces get() = namespaceMap.values.toList()

    class Namespace(val kclass: KClass<*>) {
        init {
            namespaceMap[kclass.simpleName!!] = this
        }

        val name get() = kclass.simpleName?:"UNKNOWN $kclass"

        val elements by lazy { kclass.memberProperties.map { Element(this, null, it) } }

        val types by lazy { kclass.nestedClasses.map { Type(this, null, it.createType(), it) } }

        class Element(val namespace: Namespace, val parent: Type?, val property: KProperty<*>) {

            val name get() = property.name

            val type get() = Type(namespace, parent, property.returnType)

            val minOccurs get() = if (type.rawType.isMarkedNullable) 0 else 1

            val maxOccurs get() = if (property.returnType.classifier == List::class) UNBOUNDED else 1

            //TODO - This should be a part of a class of generators.
            fun asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) = PropertySpec.builder(
                name,
                type.typeName
            ).addModifiers(modifiers.toList() ).mutable(mutable)

        }

        class Type(
            val namespace: Namespace, val parent: Type?,
            val kType: KType, val kClass: KClass<*>? = null
        ) {

            val name: String get() = if (kClass==null) "UNKNOWN3" else kClass.simpleName?:"UNKNOWN2"

            //val name get() = kClass?.simpleName
            //    ?:throw Exception("No KClass for $kType")

            private val memberProperties get() = kClass?.memberProperties?:emptyList()
            val elements by lazy { memberProperties.map { Element(namespace, parent, it) } }

            private val nestedClasses get() = kClass?.nestedClasses?:emptyList()
            val types by lazy { nestedClasses.map { Type(namespace, this, it.createType(), it) } }

            val rawType get() = if (kType.classifier == List::class)
                kType.arguments[0].type!!
            else
                kType

            val typeName get() = rawType.asTypeName()

            val nullable get() = rawType.isMarkedNullable

            val path = if (parent==null) name else "${namespace.name}/${parent.name}/$name"
            //TODO - dotPath could take an aspect as a parameter
            fun dotPath(aspect: String = "") = if (parent==null) name else "${namespace.name}$aspect.${parent.name}.$name"

            val packageName = "Fancy" //namespace.name
        }
    }

}