import com.authzee.kotlinguice4.getInstance
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.coroutine.*
import com.mongodb.ConnectionString
import model.db.JohnnySeedsDb
import org.litote.kmongo.reactivestreams.KMongo
import services.JohnnySeedsService
import services.ShoppingListService


class ApplicationModule : AbstractModule() {
    //TODO -  this should be loaded via dependency injection
    fun database(): CoroutineDatabase {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client = if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient().coroutine
        return client.getDatabase(connectionString?.database ?: "test")
    }
    override fun configure() {
        DatabaseFactory.init()
        bind(CoroutineDatabase::class.java).toInstance(database())
    }
}

fun main() {
    val injector: Injector = Guice.createInjector(ApplicationModule())
    val shoppingListService = injector.getInstance<ShoppingListService>()
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
            route(JohnnySeeds.DetailedSeed.path) {
                get {
                    call.respond(JohnnySeedsDb.DetailedSeed.fetchAll())
                }
            }
            route(JohnnySeeds.Category.path) {
                get {
                    call.respond(JohnnySeedsDb.Category.fetchAll())
                }
            }
            route(JohnnySeeds.BasicSeed.path) {
                get {
                    call.respond(JohnnySeedsDb.BasicSeed.fetchAll())
                }
            }
            route(JohnnySeeds.SeedFacts.path) {
                get {
                    call.respond(JohnnySeedsDb.SeedFacts.fetchAll())
                }
            }
            route(ShoppingListItem.path) {
                get {
                    call.respond(shoppingListService.get())
                }
                post {
                    val item = call.receive<ShoppingListItem>()
                    shoppingListService.post(item)
                    call.respond(HttpStatusCode.OK)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
                    shoppingListService.deleteOne(id)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }.start(wait = true)
}