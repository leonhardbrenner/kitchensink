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

object ShoppingListApi {
    suspend fun get(): List<ShoppingListItem> {
        return jsonClient.get(endpoint + ShoppingListItem.path)
    }

    suspend fun addItem(shoppingListItem: ShoppingListItem) {
        jsonClient.post<Unit>(endpoint + ShoppingListItem.path) {
            contentType(ContentType.Application.Json)
            body = shoppingListItem
        }
    }

    suspend fun delete(shoppingListItem: ShoppingListItem) {
        jsonClient.delete<Unit>(endpoint + ShoppingListItem.path + "/${shoppingListItem.id}")
    }

}

object SeedsApi {
    suspend fun getDetailedSeed(): List<SeedsDto.DetailedSeed> {
        return jsonClient.get(endpoint + SeedsDto.DetailedSeed.path)
    }

    suspend fun getCategory(): List<SeedsDto.SeedCategory> {
        return jsonClient.get(endpoint + SeedsDto.SeedCategory.path)
    }

    suspend fun getBasicSeed(): List<SeedsDto.BasicSeed> {
        return jsonClient.get(endpoint + SeedsDto.BasicSeed.path)
    }

    suspend fun getSeedFacts(): List<SeedsDto.SeedFacts> {
        return jsonClient.get(endpoint + SeedsDto.SeedFacts.path)
    }
}

object DvdRentalApi {

    suspend fun getActor(): List<DvdRentalDto.Actor> {
        return jsonClient.get(endpoint + DvdRentalDto.Actor.path)
    }

    suspend fun getAddress(): List<DvdRentalDto.Address> {
        return jsonClient.get(endpoint + DvdRentalDto.Address.path)
    }

    suspend fun getCategory(): List<DvdRentalDto.Category> {
        return jsonClient.get(endpoint + DvdRentalDto.Category.path)
    }

    suspend fun getCity(): List<DvdRentalDto.City> {
        return jsonClient.get(endpoint + DvdRentalDto.City.path)
    }

    suspend fun getCountry(): List<DvdRentalDto.Country> {
        return jsonClient.get(endpoint + DvdRentalDto.Country.path)
    }

    suspend fun getCustomer(): List<DvdRentalDto.Customer> {
        return jsonClient.get(endpoint + DvdRentalDto.Customer.path)
    }

    suspend fun getFilm(): List<DvdRentalDto.Film> {
        return jsonClient.get(endpoint + DvdRentalDto.Film.path)
    }

    suspend fun getFilmActor(): List<DvdRentalDto.FilmActor> {
        return jsonClient.get(endpoint + DvdRentalDto.FilmActor.path)
    }

    suspend fun getFilmCategory(): List<DvdRentalDto.FilmCategory> {
        return jsonClient.get(endpoint + DvdRentalDto.FilmCategory.path)
    }

    suspend fun getInventory(): List<DvdRentalDto.Inventory> {
        return jsonClient.get(endpoint + DvdRentalDto.Inventory.path)
    }

    suspend fun getLanguage(): List<DvdRentalDto.Language> {
        return jsonClient.get(endpoint + DvdRentalDto.Language.path)
    }

    suspend fun getPayment(): List<DvdRentalDto.Payment> {
        return jsonClient.get(endpoint + DvdRentalDto.Payment.path)
    }

    suspend fun getRental(): List<DvdRentalDto.Rental> {
        return jsonClient.get(endpoint + DvdRentalDto.Rental.path)
    }

    suspend fun getStaff(): List<DvdRentalDto.Staff> {
        return jsonClient.get(endpoint + DvdRentalDto.Staff.path)
    }

    suspend fun getStore(): List<DvdRentalDto.Store> {
        return jsonClient.get(endpoint + DvdRentalDto.Store.path)
    }

}