import TestLists.ComponentStyles.inline
import TestLists.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.transitions.mCollapse
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
        listDemoPath -> testLists()
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

    more(items.map { item -> item.name to item })
}

val category = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.Category>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsCategory())
        }
    }

    more(items.map { item -> item.name to item })
}

val basicSeed = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.BasicSeed>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsBasicSeed())
        }
    }

    more(items.map { item -> item.name to item })
}

val seedFacts = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.SeedFacts>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsSeedFacts())
        }
    }

    more(items.map { item -> item.name to item })
}

fun RBuilder.more(items: List<Pair<String, Any>>) = ul {
    items.forEach { (name, item) -> li { +"$name" } }
}

class TestLists : RComponent<RProps, RState>() {
    private var expanded: Boolean = false
    private var checked = Array(3) { false }
    private var selected = 0

    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val listDiv by css {
            display = Display.inlineFlex
            padding(1.spacingUnits)
        }

        val inline by css {
            display = Display.inlineBlock
        }
    }

    private fun getNameForImageNr(i: Int): String {
        return when(i) {
            0 -> "contemplative-reptile.jpg"
            1 -> "paella.jpg"
            else -> "live-from-space.jpg"
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
                    mListItemWithAvatar("/images/cards/contemplative-reptile.jpg", "Brunch this weekend?",
                            "Ali Connors  — I'll be in your neighborhood doing errands this…",
                            alignItems = MListItemAlignItems.flexStart)

                    mListItem( alignItems = MListItemAlignItems.flexStart, button = true) {
                        mListItemAvatar(src = "/images/cards/contemplative-reptile.jpg")
                        mListItemText( builder2.span {+"Brunch this weekend?"}, builder2.span {
                            mTypography("Ali Connors", component = "span", variant = MTypographyVariant.body2) { css (inline) }
                            +" — I'll be in your neighborhood doing errands this…"
                        })
                    }
                    mListItem(alignItems = MListItemAlignItems.flexStart, button = true) {
                        mListItemAvatar(src = "/images/cards/live-from-space.jpg")
                        mListItemText( builder2.span {+"Summer BBQ"}, builder2.span {
                            mTypography("Scott, Alex, Jennifer", component = "span", variant = MTypographyVariant.body2) { css (inline) }
                            +" — Note that this is a longer item, but it has alignItems to flexStart so the icon is at the top…"
                        })
                    }
                    mListItem(alignItems = MListItemAlignItems.center, button = true) {
                        mListItemAvatar(src = "/images/cards/paella.jpg")
                        mListItemText( builder2.span {+"Oui Oui"}, builder2.span {
                            mTypography("Sandra Adams", component = "span", variant = MTypographyVariant.body2) { css (inline) }
                            +" — Note that this item has AlignItems at center - note the icon position relative to the list item"
                        })
                    }
                }
            }
        }
    }
}

fun RBuilder.testLists() = child(TestLists::class) {}
