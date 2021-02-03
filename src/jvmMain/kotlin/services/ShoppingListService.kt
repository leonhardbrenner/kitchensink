package services

import models.ShoppingListItem
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import javax.inject.Inject

class ShoppingListService @Inject constructor(val database: CoroutineDatabase) {
    val collection
    get() = database.getCollection<ShoppingListItem>()

    //TODO - feels wrong to put suspend in the
    suspend fun get() = collection.find().toList()
    suspend fun post(item: ShoppingListItem) = collection.insertOne(item)
    suspend fun deleteOne(id: Int) = collection.deleteOne(ShoppingListItem::id eq id)
}