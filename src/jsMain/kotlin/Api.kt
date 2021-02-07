import generated.model.DvdRentalDto
import generated.model.SeedsDto
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window
import models.ShoppingListItem

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getShoppingList(): List<ShoppingListItem> {
    return jsonClient.get(endpoint + ShoppingListItem.path)
}

suspend fun addShoppingListItem(shoppingListItem: ShoppingListItem) {
    jsonClient.post<Unit>(endpoint + ShoppingListItem.path) {
        contentType(ContentType.Application.Json)
        body = shoppingListItem
    }
}

suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
    jsonClient.delete<Unit>(endpoint + ShoppingListItem.path + "/${shoppingListItem.id}")
}

suspend fun getSeedsDetailedSeed(): List<SeedsDto.DetailedSeed> {
    return jsonClient.get(endpoint + SeedsDto.DetailedSeed.path)
}

suspend fun getSeedsCategory(): List<SeedsDto.SeedCategory> {
    return jsonClient.get(endpoint + SeedsDto.SeedCategory.path)
}

suspend fun getSeedsBasicSeed(): List<SeedsDto.BasicSeed> {
    return jsonClient.get(endpoint + SeedsDto.BasicSeed.path)
}

suspend fun getSeedsSeedFacts(): List<SeedsDto.SeedFacts> {
    return jsonClient.get(endpoint + SeedsDto.SeedFacts.path)
}

suspend fun getDvdRentalActor(): List<DvdRentalDto.actor> {
    return jsonClient.get(endpoint + DvdRentalDto.actor.path)
}

suspend fun getDvdRentalAddress(): List<DvdRentalDto.address> {
    return jsonClient.get(endpoint + DvdRentalDto.address.path)
}

suspend fun getDvdRentalCategory(): List<DvdRentalDto.category> {
    return jsonClient.get(endpoint + DvdRentalDto.category.path)
}