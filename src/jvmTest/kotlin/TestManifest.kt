import org.junit.Test

class TestManifest {

    @Test
    fun test () {
        val manifest = Manifest()
        manifest {
            namespace("BuckySoap") {
                element("ElementAndComplexType") {
                    element("boolean", "builtin:boolean")
                    element("int", "builtin:int")
                    element("long", "builtin:long")
                    element("string", "builtin:string")
                }
                complexType("ComplexType") {
                    attribute("booleanAttribute", "builtin:boolean")
                    element("booleanElement", "builtin:boolean")
                    element("int", "builtin:int")
                    element("long", "builtin:long")
                    element("string", "builtin:string")
                }
                element("element", "BuckySoap:ComplexType")
            }
        }
        manifest {
            namespaceMap.values.forEach { namespace ->
                println(namespace.name)
                namespace.simpleTypeMap.values.forEach { type ->
                    println(type.name)
                }
                namespace.complexTypeMap.values.forEach { type ->
                    println(type.name)
                    type.elements.values.forEach { element ->
                        println(with(element) { "$name ${type.namespace.name} ${type.name}" })
                    }
                }
                namespace.simpleTypeMap.values.forEach { type ->
                    println(type.name)
                }
            }
        }

    }

}