import applications.DvdRentalApplication
import applications.SeedsApplication
import applications.ShoppingListApplication
import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    DatabaseFactory.init()
    val shoppingListApplication = Guice.createInjector(ShoppingListApplication.Module)
        .getInstance<ShoppingListApplication>()
    val johnnySeedsApplication = Guice.createInjector(SeedsApplication.Module)
        .getInstance<SeedsApplication>()
    val dvdRentalApplication = Guice.createInjector(DvdRentalApplication.Module)
        .getInstance<DvdRentalApplication>()

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

            shoppingListApplication.routesFrom(this)
            johnnySeedsApplication.routesFrom(this)
            dvdRentalApplication.routesFrom(this)

        }
    }.start(wait = true)
}
