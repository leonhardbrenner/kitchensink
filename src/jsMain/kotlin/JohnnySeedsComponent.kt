import TestLists.ComponentStyles.inline
import TestLists.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import io.ktor.utils.io.*
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

val JohnnySeedsComponent = functionalComponent<RProps> { _ ->
    val (age, setAge) = useState<Any>(JohnnySeeds.DetailedSeed.path)

    val inputProps: RProps = jsObject { }
    inputProps.asDynamic().name = "age"
    inputProps.asDynamic().id = "age-simple"
    val listDemoPath = "list.demo.path"
    mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
        attrs.inputProps = inputProps
        mMenuItem("Detailed Seed", value = JohnnySeeds.DetailedSeed.path)
        mMenuItem("Category", value = JohnnySeeds.Category.path)
        mMenuItem("Basic Seed", value = JohnnySeeds.BasicSeed.path)
        mMenuItem("Seed Fact", value = JohnnySeeds.SeedFacts.path)
        mMenuItem("List Demo", value = listDemoPath)
    }
    when (age) {
        JohnnySeeds.DetailedSeed.path -> child(detailedSeed)
        JohnnySeeds.Category.path -> child(category)
        JohnnySeeds.BasicSeed.path -> child(basicSeed)
        JohnnySeeds.SeedFacts.path -> child(seedFacts)
    }

}

fun RBuilder.johnnySeeds() = child(JohnnySeedsComponent) {}

val detailedSeed = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.DetailedSeed>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsDetailedSeed())
        }
    }
    testLists {
        this.items = items.map { item ->
            item.name to item.secondary_name!!
        }
    }
}

val category = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.Category>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsCategory())
        }
    }
    testLists {
        this.items = items.map { item ->
            item.name to item.image
        }
    }
}

val basicSeed = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.BasicSeed>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsBasicSeed())
        }
    }
    testLists {
        this.items = items.map { item ->
            item.name to item.image
        }
    }
}

val seedFacts = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.SeedFacts>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsSeedFacts())
        }
    }
    testLists {
        this.items = items.map { item ->
            item.name to item.maturity!!
        }
    }
}

external interface TestListsProps: RProps {
    var items: List<Pair<String, String>>
}
external interface TestListsState: RState {
    var currentSeed: String
}
class TestLists(props: TestListsProps) : RComponent<TestListsProps, TestListsState>() {
    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val listDiv by css {
            display = Display.inlineFlex
            padding(1.spacingUnits)
        }

        val inline by css {
            display = Display.inlineBlock
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
                    props.items.forEach { (name, callback) ->
                        mListItem(alignItems = MListItemAlignItems.flexStart, button = true, onClick = {
                            setState {
                                currentSeed = callback
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
}

fun RBuilder.testLists(handler: TestListsProps.() -> Unit) = child(TestLists::class) { attrs { handler() } }