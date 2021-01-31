package schema

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import java.util.*
import kotlin.reflect.KClass

class Type(val kClass: KClass<*>? = null, val nullable: Boolean = false)
class Ref(val element: Element? = null, val nullable: Boolean = false)

//TODO - add Ref and Link so we can represent the use of another type and relationship between tables
interface IElement {
    val parent: Element?
    val name: String
    val type: KClass<*>?
    val nullable: Boolean
    val path: String
    val children: List<Element>
    fun asPropertySpec(mutable: Boolean = false, vararg modifiers: KModifier): PropertySpec.Builder
}

class Element(
    override val parent: Element?,
    override val name: String,
    override val type: KClass<*>? = null, //Swap for ClassName
    val ref: Element? = null,
    override val nullable: Boolean = false,
    val block: Element.() -> Unit = {}
): IElement {
    override val children = LinkedList<Element>()
    init { block() }

    fun Element(
        name: String, type: KClass<*>? = null,
        ref: Element? = null,
        nullable: Boolean = false,
        block: (Element).() -> Unit = {}
    ) =
        children.add(Element(this, name, type, ref, nullable, block))

    val isManifest: Boolean
        get() = parent == null

    val isNamespace: Boolean
        get() = !isManifest && parent!!.isManifest

    val isRoot: Boolean
        get() = parent != null && parent.isNamespace

    override val path: String
        get() = if (isManifest) "" else "${parent!!.path}/$name"

    val isSlot: Boolean
        get() = type != null || ref!=null

    override fun asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) = PropertySpec.builder(
        name,
        type!!.asTypeName().copy(nullable = nullable)
    ).addModifiers(modifiers.map { it } ).mutable(mutable)

}

fun Manifest(name: String, block: Element.() -> Unit) =
    Element(null, name, null, null, false, block)

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
