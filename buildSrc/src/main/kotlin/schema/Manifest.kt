package schema

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

const val UNBOUNDED = Int.MAX_VALUE

object Manifest {

    val namespaceMap = HashMap<String, Namespace>()

    val namespaces get() = namespaceMap.values.toList()

    class Namespace(val kclass: KClass<*>) {
        init {
            namespaceMap[kclass.simpleName!!] = this
        }

        val name get() = kclass.simpleName?:"UNKNOWN $kclass"

        val elements by lazy { kclass.declaredMemberProperties.map { Element(this, null, it) } }

        val types by lazy { kclass.nestedClasses.map { Type(this, null, it.createType(), it) } }

        class Element(val namespace: Namespace, val parent: Type?, val property: KProperty<*>) {

            //XXX - I am guessing we only need one or there is more to one of these implementations.
            val dbName = name//.toLowerCase()
            val columnName = name//.toLowerCase()

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

            val name: String get() = if (kClass==null)
                rawType.toString().split(".").last().replace("?", "")
            else
                (kClass.simpleName?:"UNKNOWN2").replace("?", "")


            private val memberProperties get() = kClass?.declaredMemberProperties?:emptyList()
            val elements by lazy {
                //TODO - Revisit! This is done so properties are generated in the order they were declared.
                val parameters = kClass!!.primaryConstructor!!.parameters.mapIndexed {
                        pos, it -> it.name to pos
                }.toMap()
                memberProperties.map { Element(namespace, parent, it) }.sortedBy { parameters[it.name] }
            }

            private val nestedClasses get() = kClass?.nestedClasses?:emptyList()
            val types by lazy { nestedClasses.map { Type(namespace, this, it.createType(), it) } }

            val rawType get() = if (kType.classifier == List::class)
                kType.arguments[0].type!!
            else
                kType

            val typeName get() = with (rawType.asTypeName()) {
                if (rawType.toString().startsWith("models."))
                    ClassName(
                        "generated.model",
                        rawType.toString()
                            .replace("models.", "")
                            .replace("?", "")
                    )
                else
                    rawType.asTypeName()
            }.copy(nullable = nullable)

            val nullable get() = rawType.isMarkedNullable

            val path: String = if (parent==null)
                "/${namespace.name}/$name"
            else
                "${parent.path}/$name"

            fun dotPath(aspect: String = ""): String = if (parent==null)
                "${namespace.name}$aspect.$name"
            else
                "${parent.dotPath(aspect)}.$name"

            val packageName = namespace.name
        }
    }

}
