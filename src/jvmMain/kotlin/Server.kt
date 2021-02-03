import applications.ShoppingListApplication
import applications.ShoppingListService
import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import com.google.inject.Injector
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import routing.JohnnySeedsRoutes
import routing.DvdRentalRoutes
import routing.shoppingListRoutes

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {

        install(ContentNegotiation) {
            json()
        }

        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }

        install(Compression) {
            gzip()
        }

        routing {

            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }

            static("/") {
                resources("")
            }

            val injector: Injector = Guice.createInjector(ShoppingListApplication())
            val shoppingListService = injector.getInstance<ShoppingListService>()

            JohnnySeedsRoutes()

            DvdRentalRoutes()

            shoppingListRoutes(shoppingListService)


        }
    }.start(wait = true)
}
