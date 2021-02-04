import DvdStoreWindow.DisplayComponent.ComponentStyles.inline
import DvdStoreWindow.DisplayComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import generated.model.DvdRental
import generated.model.DvdRentalDto
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

fun RBuilder.dvdStore() = child(DvdStoreWindow.Component) {}

object DvdStoreWindow {

    val Component = functionalComponent<RProps> { _ ->
        val (age, setAge) = useState<Any>(DvdRentalDto.actor.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "age"
        inputProps.asDynamic().id = "age-simple"
        mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Actor", value = DvdRentalDto.actor.path)
            mMenuItem("Address", value = DvdRentalDto.address.path)
            mMenuItem("Category", value = DvdRentalDto.category.path)
        }
        when (age) {
            DvdRentalDto.actor.path -> actor {}
            DvdRentalDto.address.path -> address {}
            DvdRentalDto.category.path -> category {}
        }

    }

    interface Props : RProps
    interface State<T> : RState {
        var items: List<Pair<String, T>>
        var currentSeed: String
    }

    private abstract class DisplayComponent<T> (props: Props) : RComponent<Props, State<T>>() {
        private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
            val listDiv by css {
                display = Display.inlineFlex
                padding(1.spacingUnits)
            }

            val inline by css {
                display = Display.inlineBlock
            }
        }

        override fun State<T>.init() {
            items = listOf()
            scope.launch {
                val seeds: List<T> = get()
                setState {
                    items = seeds.map { it.label() to it }
                }
            }
        }

        override fun RBuilder.render() {
            // For building things that we don't want to render now (e.g. the component will render it later), we need another builder
            val builder2 = RBuilder()
            themeContext.Consumer { theme ->
                val themeStyles = object : StyleSheet("ComponentStyles", isStatic = true) {
                    val list by css {
                        width = 320.px
                        backgroundColor = Color(theme.palette.background.paper)
                    }
                }
                styledDiv {
                    css(listDiv)
                    mList {
                        css(themeStyles.list)
                        state.items.forEach { (name, callback) ->
                            mListItem(alignItems = MListItemAlignItems.flexStart, button = true, onClick = {
                                setState {
                                    currentSeed = callback.transform()
                                }
                            }) {
                                mListItemText(builder2.span { +name }, builder2.span {
                                    mTypography(name + " again", component = "span", variant = MTypographyVariant.body2) { css(inline) }
                                })
                            }
                        }
                    }
                    mContainer {
                        mTypography(state.currentSeed, component = "span", variant = MTypographyVariant.body2) { css(inline) }
                    }
                }
            }
        }
        abstract suspend fun get(): List<T>
        abstract fun T.label(): String
        abstract fun T.transform(): String

    }

    private class Actor(props: Props): DisplayComponent<DvdRental.actor>(props) {
        override suspend fun get(): List<DvdRental.actor> = getDvdRentalActor()
        override fun DvdRental.actor.label() = "$first_name $last_name"
        override fun DvdRental.actor.transform() = actor_id.toString()
    }
    fun RBuilder.actor(handler: Props.() -> Unit) = child(Actor::class) { attrs { handler() } }

    private class Address(props: Props): DisplayComponent<DvdRental.address>(props) {
        override suspend fun get(): List<DvdRental.address> = getDvdRentalAddress()
        override fun DvdRental.address.label() = "$address $phone"
        override fun DvdRental.address.transform() = address_id.toString()
    }
    fun RBuilder.address(handler: Props.() -> Unit) = child(Address::class) { attrs { handler() } }

    private class Category(props: Props): DisplayComponent<DvdRental.category>(props) {
        override suspend fun get(): List<DvdRental.category> = getDvdRentalCategory()
        override fun DvdRental.category.label() = "$name"
        override fun DvdRental.category.transform() = category_id.toString()
    }
    fun RBuilder.category(handler: Props.() -> Unit) = child(Category::class) { attrs { handler() } }
}
