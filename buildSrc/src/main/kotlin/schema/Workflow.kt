package schema

import java.util.*

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
