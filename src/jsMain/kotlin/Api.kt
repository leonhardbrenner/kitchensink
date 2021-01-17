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

suspend fun getJohnnySeedsDetailedSeed(): List<JohnnySeedsDto.DetailedSeedDto> {
    return jsonClient.get(endpoint + JohnnySeedsDto.DetailedSeedDto.path)
}

suspend fun getJohnnySeedsCategory(): List<JohnnySeedsDto.CategoryDto> {
    return jsonClient.get(endpoint + JohnnySeedsDto.CategoryDto.path)
}

suspend fun getJohnnySeedsBasicSeed(): List<JohnnySeedsDto.BasicSeedDto> {
    return jsonClient.get(endpoint + JohnnySeedsDto.BasicSeedDto.path)
}

suspend fun getJohnnySeedsSeedFacts(): List<JohnnySeedsDto.SeedFactsDto> {
    return jsonClient.get(endpoint + JohnnySeedsDto.SeedFactsDto.path)
}
