import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*

private val scope = MainScope()

val JohnnySeedsComponent = functionalComponent<RProps> { _ ->
    val (age, setAge) = useState<Any>(JohnnySeeds.DetailedSeed.path)

    val inputProps: RProps = jsObject { }
    inputProps.asDynamic().name = "age"
    inputProps.asDynamic().id = "age-simple"

    val detailedSeed = functionalComponent<RProps> { _ ->
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

    val category = functionalComponent<RProps> { _ ->
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

    val basicSeed = functionalComponent<RProps> { _ ->
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

    val seedFacts = functionalComponent<RProps> { _ ->
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

    mSelect(age, name = "age", onChange = { event, _ -> setAge(event.targetValue) }) {
        attrs.inputProps = inputProps
        mMenuItem("Detailed Seed", value = JohnnySeeds.DetailedSeed.path)
        mMenuItem("Category", value = JohnnySeeds.Category.path)
        mMenuItem("Basic Seed", value = JohnnySeeds.BasicSeed.path)
        mMenuItem("Seed Fact", value = JohnnySeeds.SeedFacts.path)
    }
    when (age) {
        JohnnySeeds.DetailedSeed.path -> child(detailedSeed)
        JohnnySeeds.Category.path -> child(category)
        JohnnySeeds.BasicSeed.path -> child(basicSeed)
        JohnnySeeds.SeedFacts.path -> child(seedFacts)
    }

}

fun RBuilder.johnnySeeds() = child(JohnnySeedsComponent) {}
