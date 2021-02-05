package schemanew

import com.squareup.kotlinpoet.*
import kotlin.collections.HashMap
import kotlin.reflect.KClass

object Manifest {

    val namespaceMap = HashMap<String, Namespace>()

    val namespaces get() = namespaceMap.values.toList()

    init {
        namespace("builtin") {
            //This deviates from XMLSchema which would make nullable an attribute of the type but in Kotlin we use String?
            simpleType("boolean", Boolean::class)
            simpleType("nullableBoolean", Boolean::class, nullable = true)
            simpleType("string", String::class)
            simpleType("nullableString", String::class, nullable = true)
            simpleType("int", Int::class)
            simpleType("nullableInt", Int::class, nullable = true)
            simpleType("long", Long::class)
            simpleType("nullableLong", Long::class, nullable = true)
            simpleType("double", Double::class)
            simpleType("nullableDouble", Double::class, nullable = true)
            simpleType("float", Float::class)
            simpleType("nullableLong", Float::class, nullable = true)
        }
    }

    fun namespace(name: String, block: Namespace.() -> Unit): Namespace {
        return if (namespaceMap.contains(name)) namespaceMap[name]!! else {
            Namespace(name, block).apply { namespaceMap[name] = this }
        }
    }
}

class Namespace(val name: String, block: Namespace.() -> Unit) {
    val simpleTypeMap = HashMap<String, SimpleType>()
    val simpleTypes get() = simpleTypeMap.values

    val complexTypeMap = HashMap<String, ComplexType>()
    val complexTypes get() = complexTypeMap.values.sortedBy { it.name }

    val typeMap = HashMap<String, Type>()
    val types get() = typeMap.values

    val elementMap = HashMap<String, Element>()
    val elements get() = elementMap.values

    init { block() }

    fun complexType(name: String, block: ComplexType.() -> Unit): ComplexType {
        val complexType = ComplexType(this, null, name, block)
        complexTypeMap[name] = complexType
        typeMap[name] = complexType
        return complexType
    }

    fun simpleType(name: String, kclass: KClass<*>?,
                   nullable: Boolean = false, block: SimpleType.() -> Unit = {}) {
        val simpleType = SimpleType(this, null, name, kclass, nullable, block)
        simpleTypeMap[name] = simpleType
        typeMap[name] = simpleType
    }

    fun element(name: String, typeRef: String? = null, block: (ComplexType.() -> Unit)? = null) {
        Element(this, null, name, typeRef, block)
    }


    //fun qname(name: String) = Qname(this, name)

}

class Qname(val namespace: Namespace, val name: String)

open class Element(
    val namespace: Namespace,
    val parent: ComplexType?,
    val name: String,
    typeRef: String?,
    val block: (ComplexType.() -> Unit)? = null
) {
    val typeRef = if (typeRef != null)
        TypeRef(namespace, typeRef)
    else
        null

    val type: Type get() = if (block != null)
        complexType(name, block)
    else
        typeRef!! //TODO - xor assert and ^^^ XXX actually I think we can assume a simple type here

    //val type get() = block?.let { block -> ComplexType(namespace, parent, name, block) }?:typeRef!! //TODO add xor assert
    init {
        if (parent != null) {
            parent.elementMap[name] = this
            type?.let { it ->
                parent.typeMap[name] = it
                if (it is ComplexType) { //TODO - Encapsulate this
                    parent.complexTypeMap[name] = it
                }
            }
        } else {
            namespace.elementMap[name] = this
            type?.let { it ->
                namespace.typeMap[name] = it
                if (it is ComplexType) { //TODO - Encapsulate this
                    namespace.complexTypeMap[name] = it
                }
            }
        }
    }
    fun complexType(name: String, block: ComplexType.() -> Unit): ComplexType {
        return if (parent != null)
            parent.complexType(name, block)
        else
            namespace.complexType(name, block)
    }
    fun asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) = PropertySpec.builder(
        name,
        type.typeName
    ).addModifiers(modifiers.toList() ).mutable(mutable)

}

open class Attribute(val namespace: Namespace, val parent: ComplexType?, val name: String, typeRef: String?) {
    val elements = HashMap<String, Element>()
    val typeRef = typeRef?.let { TypeRef(namespace, it) }
}

interface Type {
    val namespace: Namespace
    val name: String
    val typeName: TypeName
}

class TypeRef(
    override val namespace: Namespace,
    override val name: String //This may be a local or qualified reference to a type.
): Type {
    override val typeName: TypeName
        get() = let {
            val indexOfColon = name.indexOf(":")
            return if (indexOfColon > 0) {
                try {Manifest.namespaceMap[name.substring(0, indexOfColon)]!!
                    .typeMap[name.substring(indexOfColon + 1)]!!.typeName} catch(ex: Exception) {
                    ClassName("", "SomethingWeirdHappend")
                }
            } else if (namespace.typeMap.containsKey(name)) {
                //XXX - I think there is a third option where the type is contained in the parent
                namespace.typeMap[name]!!.typeName
            } else {
                ClassName("", "")
            }
        }
}

open class ComplexType(
    override val namespace: Namespace,
    val parent: ComplexType?,
    override val name: String,
    block: ComplexType.() -> Unit
): Type {
    val typeMap = HashMap<String, Type>()
    val types get() = typeMap.values
    val complexTypeMap = HashMap<String, ComplexType>()
    val complexTypes = complexTypeMap.values
    val elementMap = HashMap<String, Element>()
    val elements get() = elementMap.values.sortedBy { it.name }.toList() //XXX - looks like not all of the geenerators are using this.
    init {
        block()
        namespace.complexTypeMap[name] = this
    }
    fun complexType(name: String, block: ComplexType.() -> Unit): ComplexType {
        val complexType = ComplexType(namespace, this, name, block)
        complexTypeMap[name] = complexType
        typeMap[name] = complexType
        return complexType
    }
    fun attribute(name: String, typeRef: String? = null) =
        Attribute(namespace, this, name, typeRef)
    fun element(name: String, typeRef: String? = null, block: (ComplexType.() -> Unit)? = null) {
        Element(namespace, this, name, typeRef, block)
    }
    override val typeName get() = ClassName("", name)
}

open class SimpleType(
    override val namespace: Namespace,
    val parent: ComplexType?,
    override val name: String,
    val kclass: KClass<*>?,
    val nullable: Boolean = false,
    block: SimpleType.() -> Unit
): Type {
    init {
        block()
        namespace.typeMap[name] = this
        namespace.simpleTypeMap[name] = this
    }
    override val typeName get() = kclass!!.asTypeName().copy(nullable = nullable)
}