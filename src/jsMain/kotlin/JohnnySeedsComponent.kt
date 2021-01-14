import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import kotlinext.js.jsObject
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import react.*
import react.dom.*
import kotlinx.coroutines.*
import org.w3c.dom.events.Event

private val scope = MainScope()

val JohnnySeedsComponent = functionalComponent<RProps> { _ ->
    val (age, setAge) = useState<Any>(JohnnySeeds.DetailedSeed.path)

    val inputProps: RProps = jsObject { }
    inputProps.asDynamic().name = "age"
    inputProps.asDynamic().id = "age-simple"

    mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
        attrs.inputProps = inputProps
        mMenuItem("Detailed Seed", value = JohnnySeeds.DetailedSeed.path)
        mMenuItem("Category", value = JohnnySeeds.Category.path)
        mMenuItem("Basic Seed", value = JohnnySeeds.BasicSeed.path)
        mMenuItem("Seed Fact", value = JohnnySeeds.SeedFacts.path)
    }
    when (age) {
        JohnnySeeds.DetailedSeed.path -> detailedSeed()
        JohnnySeeds.Category.path -> category()
        JohnnySeeds.BasicSeed.path -> basicSeed()
        JohnnySeeds.SeedFacts.path -> seedFacts()
    }
}

fun RBuilder.johnnySeeds() = child(JohnnySeedsComponent) {}

val JohnnySeedsDetailedSeedComponent = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.DetailedSeed>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsDetailedSeed())
        }
    }

    ul {
        items.forEachIndexed { i, it ->
            li {
                +"[$i] ${it.name}"
            }
        }
    }
}
fun RBuilder.detailedSeed() = child(JohnnySeedsDetailedSeedComponent) {}

val JohnnySeedsCategoryComponent = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.Category>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsCategory())
        }
    }

    ul {
        items.forEachIndexed { i, it ->
            li {
                +"[$i] ${it.name}"
            }
        }
    }
}
fun RBuilder.category() = child(JohnnySeedsCategoryComponent) {}

val JohnnySeedsBasicSeedComponent = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.BasicSeed>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsBasicSeed())
        }
    }

    ul {
        items.forEachIndexed { i, it ->
            li {
                +"[$i] ${it.name}"
            }
        }
    }
}
fun RBuilder.basicSeed() = child(JohnnySeedsBasicSeedComponent) {}

val JohnnySeedsSeedFactsComponent = functionalComponent<RProps> { _ ->
    val (items, setItems) = useState(emptyList<JohnnySeeds.SeedFacts>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setItems(getJohnnySeedsSeedFacts())
        }
    }

    ul {
        items.forEachIndexed { i, it ->
            li {
                +"[$i] ${it.name}"
            }
        }
    }
}
fun RBuilder.seedFacts() = child(JohnnySeedsSeedFactsComponent) {}
