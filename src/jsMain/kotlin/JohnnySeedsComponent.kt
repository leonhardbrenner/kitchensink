import JohnnySeedsWindow.DisplayComponent.ComponentStyles.inline
import JohnnySeedsWindow.DisplayComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
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
        val (age, setAge) = useState<Any>(JohnnySeedsDto.DetailedSeedDto.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "age"
        inputProps.asDynamic().id = "age-simple"
        val listDemoPath = "list.demo.path"
        mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Detailed Seed", value = JohnnySeedsDto.DetailedSeedDto.path)
            mMenuItem("Category", value = JohnnySeedsDto.CategoryDto.path)
            mMenuItem("Basic Seed", value = JohnnySeedsDto.BasicSeedDto.path)
            mMenuItem("Seed Fact", value = JohnnySeedsDto.SeedFactsDto.path)
        }
        when (age) {
            JohnnySeedsDto.DetailedSeedDto.path -> detailedSeed {}
            JohnnySeedsDto.CategoryDto.path -> category {}
            JohnnySeedsDto.BasicSeedDto.path -> basicSeed {}
            JohnnySeedsDto.SeedFactsDto.path -> seedFacts {}
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

    private class DetailedSeed(props: Props): DisplayComponent<JohnnySeedsDto.DetailedSeedDto>(props) {
        override suspend fun get(): List<JohnnySeedsDto.DetailedSeedDto> = getJohnnySeedsDetailedSeed()
        override fun JohnnySeedsDto.DetailedSeedDto.label() = name
        override fun JohnnySeedsDto.DetailedSeedDto.transform() = name
    }
    fun RBuilder.detailedSeed(handler: Props.() -> Unit) = child(DetailedSeed::class) { attrs { handler() } }

    private class Category(props: Props): DisplayComponent<JohnnySeedsDto.CategoryDto>(props) {
        override suspend fun get(): List<JohnnySeedsDto.CategoryDto> = getJohnnySeedsCategory()
        override fun JohnnySeedsDto.CategoryDto.label() = name
        override fun JohnnySeedsDto.CategoryDto.transform() = image
    }
    fun RBuilder.category(handler: Props.() -> Unit) = child(Category::class) { attrs { handler() } }

    private class BasicSeed(props: Props): DisplayComponent<JohnnySeedsDto.BasicSeedDto>(props) {
        override suspend fun get(): List<JohnnySeedsDto.BasicSeedDto> = getJohnnySeedsBasicSeed()
        override fun JohnnySeedsDto.BasicSeedDto.label() = name
        override fun JohnnySeedsDto.BasicSeedDto.transform() = image
    }
    fun RBuilder.basicSeed(handler: Props.() -> Unit) = child(BasicSeed::class) { attrs { handler() } }

    private class SeedFacts(props: Props): DisplayComponent<JohnnySeedsDto.SeedFactsDto>(props) {
        override suspend fun get(): List<JohnnySeedsDto.SeedFactsDto> = getJohnnySeedsSeedFacts()
        override fun JohnnySeedsDto.SeedFactsDto.label() = name
        override fun JohnnySeedsDto.SeedFactsDto.transform() = maturity!!
    }
    fun RBuilder.seedFacts(handler: Props.() -> Unit) = child(SeedFacts::class) { attrs { handler() } }
}
