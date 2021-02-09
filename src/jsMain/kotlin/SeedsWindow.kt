import SeedsWindow.DisplayComponent.ComponentStyles.inline
import SeedsWindow.DisplayComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import generated.model.Seeds
import generated.model.SeedsDto
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

fun RBuilder.seeds() = child(SeedsWindow.Component) {}

object SeedsWindow {

    val Component = functionalComponent<RProps> { _ ->
        val (age, setAge) = useState<Any>(SeedsDto.DetailedSeed.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "age"
        inputProps.asDynamic().id = "age-simple"
        mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Detailed Seed", value = SeedsDto.DetailedSeed.path)
            mMenuItem("Category", value = SeedsDto.SeedCategory.path)
            mMenuItem("Basic Seed", value = SeedsDto.BasicSeed.path)
            mMenuItem("Seed Fact", value = SeedsDto.SeedFacts.path)
        }
        when (age) {
            SeedsDto.DetailedSeed.path -> detailedSeed {}
            SeedsDto.SeedCategory.path -> category {}
            SeedsDto.BasicSeed.path -> basicSeed {}
            SeedsDto.SeedFacts.path -> seedFacts {}
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

    private class DetailedSeed(props: Props): DisplayComponent<Seeds.DetailedSeed>(props) {
        override suspend fun get(): List<Seeds.DetailedSeed> = SeedsApi.getDetailedSeed()
        override fun Seeds.DetailedSeed.label() = name
        override fun Seeds.DetailedSeed.transform() = name
    }
    fun RBuilder.detailedSeed(handler: Props.() -> Unit) = child(DetailedSeed::class) { attrs { handler() } }

    private class Category(props: Props): DisplayComponent<Seeds.SeedCategory>(props) {
        override suspend fun get(): List<Seeds.SeedCategory> = SeedsApi.getCategory()
        override fun Seeds.SeedCategory.label() = name
        override fun Seeds.SeedCategory.transform() = image
    }
    fun RBuilder.category(handler: Props.() -> Unit) = child(Category::class) { attrs { handler() } }

    private class BasicSeed(props: Props): DisplayComponent<Seeds.BasicSeed>(props) {
        override suspend fun get(): List<Seeds.BasicSeed> = SeedsApi.getBasicSeed()
        override fun Seeds.BasicSeed.label() = name
        override fun Seeds.BasicSeed.transform() = image
    }
    fun RBuilder.basicSeed(handler: Props.() -> Unit) = child(BasicSeed::class) { attrs { handler() } }

    private class SeedFacts(props: Props): DisplayComponent<Seeds.SeedFacts>(props) {
        override suspend fun get(): List<Seeds.SeedFacts> = SeedsApi.getSeedFacts()
        override fun Seeds.SeedFacts.label() = name
        override fun Seeds.SeedFacts.transform() = maturity!!
    }
    fun RBuilder.seedFacts(handler: Props.() -> Unit) = child(SeedFacts::class) { attrs { handler() } }
}
