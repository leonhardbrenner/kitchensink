package schema

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import java.util.*
import kotlin.reflect.KClass

interface Element {
    val parent: X?
    val name: String
    val type: KClass<*>?
    val ref: Element?
    val nullable: Boolean
    val children: List<Element>
    val isManifest: Boolean
        get() = parent == null
    val isNamespace: Boolean
        get() = !isManifest && parent!!.isManifest
    val isRoot: Boolean
        get() = parent != null && parent!!.isNamespace
    val path: String
        get() = if (isManifest) "" else "${parent!!.path}/$name"
    val isSlot: Boolean
        get() = type != null || ref!=null
    fun asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) = PropertySpec.builder(
        name,
        type!!.asTypeName().copy(nullable = nullable)
    ).addModifiers(modifiers.map { it } ).mutable(mutable)

    class X(
        override val parent: X?,
        override val name: String,
        override val type: KClass<*>? = null, //Swap for ClassName
        override val ref: X? = null,
        override val nullable: Boolean = false,
        block: X.() -> Unit = {}
    ): Element {
        override val children = LinkedList<Element>()
        init { block() }
        fun x(
            name: String, type: KClass<*>? = null,
            ref: X? = null,
            nullable: Boolean = false,
            block: (X).() -> Unit = {}
        ) = children.add(
            X(this, name, type, ref, nullable, block)
        )
    }

    class Model(element: Element): Element by element {
        val namespaces by lazy {
            children.map { Namespace(it)}
        }
        inner class Namespace(element: Element): Element by element {
            val types by lazy {
                children.map { Type(this, it) }
            }
        }
        inner class Type(val namespace: Namespace, element: Element): Element by element {
            val slots by lazy {
                children.map { Slot(it) }//.sortedBy { it.name }
            }
            val packageName get() = parent?.path?.substring(1)?.replace("/", ".")?:""
        }
        inner class Slot(element: Element): Element by element {
            val columnName get() = name
        }
    }

    companion object {
        fun model(name: String, block: X.() -> Unit) = Model(
            X(null, name, null, null, false, block)
        )
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
