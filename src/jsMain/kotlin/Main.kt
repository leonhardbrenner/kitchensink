import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.dialog.mDialog
import com.ccfraser.muirwik.components.mThemeProvider
import com.ccfraser.muirwik.components.styles.mStylesProvider
import react.dom.render
import kotlinx.browser.document
import kotlinx.css.*
import styled.css
import styled.styledDiv

//TODO - look into hot-loader
//@JsModule("react-hot-loader")
//private external val hotModule: dynamic
//private val hot = hotModule.hot
//private val module = js("module")
fun main() {
    render(document.getElementById("root")) {
        //TODO - look into hot-loader
        //val hotWrapper = hot(module)
        app()
    }
}