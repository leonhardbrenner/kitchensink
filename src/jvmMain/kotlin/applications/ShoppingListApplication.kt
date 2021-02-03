package applications

import com.authzee.kotlinguice4.getInstance
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import models.ShoppingListItem
import org.litote.kmongo.eq
import javax.inject.Inject

class ShoppingListService @Inject constructor(val database: CoroutineDatabase) {
    val collection
        get() = database.getCollection<ShoppingListItem>()

    suspend fun get() = collection.find().toList()
    suspend fun post(item: ShoppingListItem) = collection.insertOne(item)
    suspend fun delete(id: Int) = collection.deleteOne(ShoppingListItem::id eq id)
}

object ShoppingListApplication : AbstractModule() {

    override fun configure() {
        DatabaseFactory.init()
        bind(CoroutineDatabase::class.java).toInstance(database())
    }

    fun database(): CoroutineDatabase {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client = connectionString?.let {
            KMongo.createClient(connectionString).coroutine
        } ?: KMongo.createClient().coroutine
        return client.getDatabase(connectionString?.database ?: "test")
    }

    val injector: Injector = Guice.createInjector(ShoppingListApplication)

    val shoppingListService = injector.getInstance<ShoppingListService>()
}
