import com.ccfraser.muirwik.components.*
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

class App : RComponent<RProps, RState>() {
    private var tab1Value: Any = "one"
    private var tab2Value: Any = 0
    private var tab3Value: Any = 1
    private var tab4Value: Any = 1

    private object CustomTabStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val tabsRoot by css {
            borderBottom = "1px solid #e8e8e8"
        }
        val tabsIndicator by css {
            backgroundColor = Color("#1890ff")
        }
        val typography by css {
            padding(3.spacingUnits)
        }
    }

    private fun RBuilder.tabContainer(text: String) {
        mTypography(text) {
            css { padding(3.spacingUnits) }
        }
    }

    override fun RBuilder.render() {
        themeContext.Consumer { theme ->
            val themeStyles = object : StyleSheet("ComponentStyles", isStatic = true) {
                val tabRoot by css {
                    textTransform = TextTransform.none
                    fontWeight = FontWeight(theme.typography.fontWeightRegular.toString())
                    marginRight = 4.spacingUnits
                    hover {
                        color = Color("#40a9ff")
                        opacity = 1
                    }
                    focus {
                        color = Color("#40a9ff")
                    }
                }
                val tabSelected by css {
                    color = Color("#1890ff")
                    fontWeight = FontWeight(theme.typography.fontWeightMedium.toString())
                }
            }

            fun RBuilder.customTab(label: String, value: Int) {
                mTab(label, value) {
                    css {
                        +themeStyles.tabRoot
                        if (tab3Value == value) {
                            +themeStyles.tabSelected
                        }
                    }
                    attrs.asDynamic().disableRipple = true
                }
            }

            styledDiv {
                css { flexGrow = 1.0; backgroundColor = Color(theme.palette.background.paper) }
                mAppBar(position = MAppBarPosition.static) {
                    mTabs(tab1Value, onChange = { _, value -> setState { tab1Value = value } }) {
                        mTab("Item One with a really long name for a tab", "one")
                        mTab("Item Two", "two")
                        mTab("Item Three", "three")
                    }
                }
                when (tab1Value) {
                    "one" -> johnnySeeds()
                    "two" -> shoppingList()
                    "three" -> materialUIDemo()
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
//import com.ccfraser.muirwik.components.*
//import com.ccfraser.muirwik.components.button.mButton
//import com.ccfraser.muirwik.components.styles.mStylesProvider
//import kotlinx.css.*
//import react.*
//import styled.css
//import styled.styledDiv
//
//
//val App = functionalComponent<RProps> { _ ->
//    val (tab1Value, setTab1Value) = useState<Any>("one")
//
//    //TODO - looks like this is how themes work
//    //themeContext.Consumer { theme ->
//    //    val themeStyles = object : StyleSheet("ComponentStyles", isStatic = true) {
//    //        val tabRoot by css {
//    //            textTransform = TextTransform.none
//    //            fontWeight = FontWeight(theme.typography.fontWeightRegular.toString())
//    //            marginRight = 4.spacingUnits
//    //            hover {
//    //                color = Color("#40a9ff")
//    //                opacity = 1
//    //            }
//    //            focus {
//    //                color = Color("#40a9ff")
//    //            }
//    //        }
//    //        val tabSelected by css {
//    //            color = Color("#1890ff")
//    //            fontWeight = FontWeight(theme.typography.fontWeightMedium.toString())
//    //        }
//    //    }
//    mStylesProvider("jss-insertion-point") {
//        mThemeProvider {
//            styledDiv {
//                css { flexGrow = 1.0 }
//                //css { flexGrow = 1.0; backgroundColor = Color(theme.palette.background.paper) }
//                mAppBar(position = MAppBarPosition.static) {
//                    mTabs(tab1Value, onChange = { _, value -> setTab1Value(value) }) {
//                        mTab("Item One with a really long name for a tab", "one")
//                        mTab("Item Two", "two")
//                        mTab("Item Three", "three")
//                    }
//                }
//                mTypography(paragraph = true) {
//                    +"""$tab1Value This content is hidden and shown by use of the mCollapse control.""".trimIndent()
//                }
//                when (tab1Value) {
//                    "one" -> { johnnySeeds() }
//                    "two" -> { shoppingList() }
//                    "three" -> { materialUIDemo() }
//                }
//            }
//            //mButton("Click me to see a different app, count=$count!", onClick = { event ->
//            //    setCount((count + 1) % 4) })
//            ////TODO - look into hot-loader
//            ////hotWrapper(app2())
//            //+ ""
//            //when (count) {
//            //    0 -> { johnnySeeds() }
//            //    1 -> { shoppingList() }
//            //    2 -> { materialUIDemo() }
//            //}
//        }
//    }
//}
//
//fun RBuilder.app() = child(App)