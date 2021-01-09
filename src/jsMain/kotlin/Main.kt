import react.dom.render
import kotlinx.browser.document

//TODO - https://github.com/gaearon/react-hot-loader
//@JsModule("react-hot-loader")
//private external val hotModule: dynamic
//private val hot = hotModule.hot
//private val module = js("module")

fun main() {
    render(document.getElementById("root")) {
        //val hotWrapper = hot(module)
        app()
    }
}