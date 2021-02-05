import kotlin.collections.HashMap
import kotlin.reflect.KClass

class Manifest {
    val namespaceMap = HashMap<String, Namespace>()
    init {
        namespace("builtin") {
            //This deviates from XMLSchema which would make nullable an attribute of the type but in Kotlin we use String?
            simpleType("string", String::class)
            simpleType("nullableString", String::class, nullable = true)
            simpleType("int", Int::class)
            simpleType("nullableString", Int::class, nullable = true)
            simpleType("long", Long::class)
            simpleType("nullableLong", Long::class, nullable = true)
            simpleType("boolean", Boolean::class)
            simpleType("nullableBoolean", Boolean::class, nullable = true)
        }
    }
    fun namespace(name: String, block: Namespace.() -> Unit) = Namespace(name, block).let { namespace ->
        val namespace = Namespace(name, block)
        namespaceMap[name] = namespace
    }
    operator fun invoke(block: Manifest.() -> Unit) {
        block()
    }
}

class Namespace(val name: String, block: Namespace.() -> Unit) {
        val simpleTypeMap = HashMap<String, SimpleType>()
        val complexTypeMap = HashMap<String, ComplexType>()
        val typeMap = HashMap<String, Type>()
        val elementMap = HashMap<String, Element>()

    init { block() }

    fun complexType(name: String, block: ComplexType.() -> Unit) {
        val complexType = ComplexType(this, null, name, block)
        complexTypeMap[name] = complexType
        typeMap[name] = complexType
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
    block: (ComplexType.() -> Unit)? = null
) {
    val typeRef = if (typeRef != null) TypeRef(namespace, typeRef) else null
    val type = if (block != null) complexType(name, block) else null
    //val type get() = block?.let { block -> ComplexType(namespace, parent, name, block) }?:typeRef!! //TODO add xor assert
    init {
        if (parent != null)
            parent.elements[name] = this
        else
            namespace.elementMap[name] = this
    }
    fun complexType(name: String, block: ComplexType.() -> Unit) {
        if (parent != null)
            parent.complexType(name, block)
        else
            namespace.complexType(name, block)
    }

}

open class Attribute(val namespace: Namespace, val parent: ComplexType?, val name: String, typeRef: String?) {
    val elements = HashMap<String, Element>()
    val typeRef = typeRef?.let { TypeRef(namespace, it) }
}

interface Type {
    val name: String
}

class TypeRef(
    val namespace: Namespace,
    override val name: String //This may be a local or qualified reference to a type.
): Type {

}

open class ComplexType(
    val namespace: Namespace,
    val parent: ComplexType?,
    override val name: String,
    block: ComplexType.() -> Unit
): Type {
    val types = HashMap<String, ComplexType>()
    val complexTypes = HashMap<String, ComplexType>()
    val elements = HashMap<String, Element>()
    init {
        block()
        namespace.complexTypeMap[name] = this
    }
    fun complexType(name: String, block: ComplexType.() -> Unit) {
        val complexType = ComplexType(namespace, this, name, block)
        complexTypes[name] = complexType
        types[name] = complexType
    }
    fun attribute(name: String, typeRef: String? = null) =
        Attribute(namespace, this, name, typeRef)
    fun element(name: String, typeRef: String? = null, block: (ComplexType.() -> Unit)? = null) {
        Element(namespace, this, name, typeRef, block)
    }
}

open class SimpleType(
    val namespace: Namespace,
    val parent: ComplexType?,
    override val name: String,
    kclass: KClass<*>?,
    nullable: Boolean = false,
    block: SimpleType.() -> Unit
): Type {
    init {
        block()
        namespace.typeMap[name] = this
    }
}