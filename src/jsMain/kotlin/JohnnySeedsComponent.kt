import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import styled.css
import styled.styledDiv

private val scope = MainScope()

val JohnnySeedsComponent = functionalComponent<RProps> { _ ->
    val (johnnySeeds, setJohnnySeeds) = useState(emptyList<JohnnySeeds.DetailedSeed>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setJohnnySeeds(getJohnnySeeds())
        }
    }

    ul {
        johnnySeeds.forEachIndexed { i, it ->
            li {
                +"[$i] ${it.name}"
            }
        }
    }
}

fun RBuilder.johnnySeeds() = child(JohnnySeedsComponent) {}