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
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import com.mongodb.ConnectionString
import org.litote.kmongo.reactivestreams.KMongo


class ShoppingListService {
    //TODO -  this should be loaded via dependency injection
    companion object {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }

        val client = if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient().coroutine
        val database = client.getDatabase(connectionString?.database ?: "test")
        val collection = database.getCollection<ShoppingListItem>()
    }
    //TODO - feels wrong to put suspend in the
    suspend fun get() = collection.find().toList()
    suspend fun post(item: ShoppingListItem) = collection.insertOne(item)
    suspend fun deleteOne(id: Int) = collection.deleteOne(ShoppingListItem::id eq id)
}


fun main() {
    val shoppingListService = ShoppingListService()
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
            route(JohnnySeeds.path) {
                get {
                    call.respond(JohnnySeedsService.DetailedSeed().fetchAll())
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