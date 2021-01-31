import JohnnySeedsWindow.DisplayComponent.ComponentStyles.inline
import JohnnySeedsWindow.DisplayComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import generated.model.JohnnySeeds
import generated.model.JohnnySeedsDto
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

fun RBuilder.johnnySeeds() = child(JohnnySeedsWindow.Component) {}

object JohnnySeedsWindow {

    val Component = functionalComponent<RProps> { _ ->
        val (age, setAge) = useState<Any>(JohnnySeedsDto.DetailedSeeds.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "age"
        inputProps.asDynamic().id = "age-simple"
        mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Detailed Seed", value = JohnnySeedsDto.DetailedSeeds.path)
            mMenuItem("Category", value = JohnnySeedsDto.Category.path)
            mMenuItem("Basic Seed", value = JohnnySeedsDto.BasicSeed.path)
            mMenuItem("Seed Fact", value = JohnnySeedsDto.SeedFacts.path)
        }
        when (age) {
            JohnnySeedsDto.DetailedSeeds.path -> detailedSeed {}
            JohnnySeedsDto.Category.path -> category {}
            JohnnySeedsDto.BasicSeed.path -> basicSeed {}
            JohnnySeedsDto.SeedFacts.path -> seedFacts {}
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

    private class DetailedSeed(props: Props): DisplayComponent<JohnnySeeds.DetailedSeeds>(props) {
        override suspend fun get(): List<JohnnySeeds.DetailedSeeds> = getJohnnySeedsDetailedSeed()
        override fun JohnnySeeds.DetailedSeeds.label() = name
        override fun JohnnySeeds.DetailedSeeds.transform() = name
    }
    fun RBuilder.detailedSeed(handler: Props.() -> Unit) = child(DetailedSeed::class) { attrs { handler() } }

    private class Category(props: Props): DisplayComponent<JohnnySeeds.Category>(props) {
        override suspend fun get(): List<JohnnySeeds.Category> = getJohnnySeedsCategory()
        override fun JohnnySeeds.Category.label() = name
        override fun JohnnySeeds.Category.transform() = image
    }
    fun RBuilder.category(handler: Props.() -> Unit) = child(Category::class) { attrs { handler() } }

    private class BasicSeed(props: Props): DisplayComponent<JohnnySeeds.BasicSeed>(props) {
        override suspend fun get(): List<JohnnySeeds.BasicSeed> = getJohnnySeedsBasicSeed()
        override fun JohnnySeeds.BasicSeed.label() = name
        override fun JohnnySeeds.BasicSeed.transform() = image
    }
    fun RBuilder.basicSeed(handler: Props.() -> Unit) = child(BasicSeed::class) { attrs { handler() } }

    private class SeedFacts(props: Props): DisplayComponent<JohnnySeeds.SeedFacts>(props) {
        override suspend fun get(): List<JohnnySeeds.SeedFacts> = getJohnnySeedsSeedFacts()
        override fun JohnnySeeds.SeedFacts.label() = name
        override fun JohnnySeeds.SeedFacts.transform() = maturity!!
    }
    fun RBuilder.seedFacts(handler: Props.() -> Unit) = child(SeedFacts::class) { attrs { handler() } }
}
