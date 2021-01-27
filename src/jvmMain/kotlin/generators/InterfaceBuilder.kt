package generators

import java.util.*

//interface X {
//    val a: Int
//    val z: Z
//    val y2: Y?
//    val z2: Z?
//    val y3: List<Y?>?
//    val z3: List<Z?>?
//    interface Y {
//        val b: String
//        val z22: List<Z?>?
//    }
//}
//}
//interface Z {
//    val c: Double
//    val d: List<String>
//}
class Element(val parent: Element?, val name: String, val block: (Element).() -> Unit = {}) {
    val children: MutableList<Element>

    init {
        children = LinkedList()
        if (parent!=null)
            parent.children.add(this)
        block(this)
    }

    fun Element(name: String, block: (Element).() -> Unit = {}) =
        Element(this, name, block)

    val path: String
        get() = if (parent == null) "/$name" else "${parent.path}/$name"

}

fun Manifest(name: String, block: (Element).() -> Unit) =
    Element(null, name, block)

class Visitor(val root: Element) {
    fun walk(element: Element = root) {
        println(element.path)
        element.children.forEach {
            walk(it)
        }
    }
}

fun main() {
    val manifest = Manifest("JohnnySeeds") {
        Element("a")
        Element("b")
        Element("Y") {
            Element("a")
            Element("b")
        }
    }
    Visitor(manifest).walk()
    //manifest.interfaces.forEach {
    //    it.elements.forEach {
    //        println(it.name)
    //    }
    //}
}