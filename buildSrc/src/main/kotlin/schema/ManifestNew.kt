package schema

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

}