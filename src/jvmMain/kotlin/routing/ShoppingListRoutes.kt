package routing

import com.authzee.kotlinguice4.getInstance
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.mongodb.ConnectionString
import models.ShoppingListItem
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import services.ShoppingListService

class ApplicationModule : AbstractModule() {

    //TODO -  this should be loaded via dependency injection
    fun database(): CoroutineDatabase {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client = connectionString?.let {
            KMongo.createClient(connectionString).coroutine
        } ?: KMongo.createClient().coroutine
        return client.getDatabase(connectionString?.database ?: "test")
    }

    override fun configure() {
        DatabaseFactory.init()
        bind(CoroutineDatabase::class.java).toInstance(database())
    }

}

val injector: Injector = Guice.createInjector(ApplicationModule())
val shoppingListService = injector.getInstance<ShoppingListService>()

fun Routing.shopingListRoutes() = route(ShoppingListItem.path) {
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