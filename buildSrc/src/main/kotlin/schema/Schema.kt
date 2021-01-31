package schema

import java.util.*
import kotlin.reflect.KClass

//This is for native types
class Type(val kClass: KClass<*>, val nullable: Boolean = false)

//TODO - add Ref and Link so we can represent the use of another type and relationship between tables
class Element(val parent: Element?, val name: String, val type: Type? = null, val block: (Element).() -> Unit = {}) {
    val children: MutableList<Element>

    init {
        children = LinkedList()
        if (parent!=null)
            parent.children.add(this)
        block(this)
    }

    fun Element(name: String, type: Type? = null, block: (Element).() -> Unit = {}) =
        Element(this, name, type, block)

    val path: String
        get() = if (parent == null) "/$name" else "${parent.path}/$name"

    val packageName: String
        get() = parent?.path?.substring(1)?.replace("/", ".")?:""
}

fun Manifest(name: String, block: (Element).() -> Unit) =
    Element(null, name, null, block)

class Visitor(val root: Element) {
    fun walk(element: Element = root) {
        println(element.path)
        element.children.forEach {
            walk(it)
        }
    }
}

class Workflow(
    val name_: String,
    val init_: String,
    val block: Workflow.() -> Unit
) {
    val states_ = LinkedList<State>()
    init { block() }
    inner class State(val name_: String, val block: State.() -> Unit) {
        var activate_: Activate? = null
        var deactivate_: Deactivate? = null
        val labels = LinkedList<Label>()
        init {
            block()
            states_.add(this)
        }
        inner class Activate(val class_: String) {
            init {
                activate_ = this
            }
        }

        inner class Deactivate(val class_: String) {
            init {
                deactivate_ = this
            }
        }

        inner class Label(
            val name_: String,
            val class_: String,
            val block: Label.() -> Unit
        ) {
            val transitions = LinkedList<Transition>()
            init {
                block()
                labels.add(this)
            }
            inner class Transition(val to_: String, val if_: String) {
                init { transitions.add(this) }
            }
        }
    }
}
