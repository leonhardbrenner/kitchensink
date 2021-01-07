import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.mThemeProvider
import com.ccfraser.muirwik.components.styles.mStylesProvider
import react.*


val App = functionalComponent<RProps> { _ ->
    val (count, setCount) = useState(0)
    mStylesProvider("jss-insertion-point") {
        mThemeProvider {
            mButton("Click me to see a different app, count=$count!", onClick = { event ->
                setCount((count + 1) % 4) })
            //TODO - look into hot-loader
            //hotWrapper(app2())
            + ""
            when (count) {
                0 -> { johnnySeeds() }
                1 -> { shoppingList() }
                2 -> { materialUIDemo() }
                3 -> { materialUIDemo2() }
            }
        }
    }
}

fun RBuilder.app() = child(App)