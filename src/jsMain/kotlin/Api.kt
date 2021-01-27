import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window

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

suspend fun getJohnnySeedsDetailedSeed(): List<JohnnySeedsDto.DetailedSeeds> {
    return jsonClient.get(endpoint + JohnnySeedsDto.DetailedSeeds.path)
}

suspend fun getJohnnySeedsCategory(): List<JohnnySeedsDto.Category> {
    return jsonClient.get(endpoint + JohnnySeedsDto.Category.path)
}

suspend fun getJohnnySeedsBasicSeed(): List<JohnnySeedsDto.BasicSeed> {
    return jsonClient.get(endpoint + JohnnySeedsDto.BasicSeed.path)
}

suspend fun getJohnnySeedsSeedFacts(): List<JohnnySeedsDto.SeedFacts> {
    return jsonClient.get(endpoint + JohnnySeedsDto.SeedFacts.path)
}
