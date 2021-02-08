package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String

interface DvdRental {
  interface Actor {
    val actorId: Int

    val firstName: String

    val lastName: String

    val lastUpdate: String
  }

  interface Address {
    val address: String

    val address2: String?

    val addressId: Int

    val cityId: Int

    val district: String?

    val lastUpdate: String

    val phone: String?

    val postalCode: String?
  }

  interface Category {
    val categoryId: Int

    val lastUpdate: String

    val name: String
  }

  interface City {
    val city: String

    val cityId: Int

    val countryId: Int

    val lastUpdate: Int
  }

  interface Country {
    val country: String

    val countryId: Int

    val lastUpdate: String
  }

  interface Customer {
    val active: Int

    val activebool: Boolean

    val addressId: Int

    val createDate: String

    val customerId: Int

    val email: String

    val firstName: String

    val lastName: String

    val lastUpdate: String

    val storeId: Int
  }

  interface Film {
    val description: String

    val filmId: Int

    val fullText: String

    val languageId: Int

    val lastUpdate: String

    val length: Int

    val rating: String

    val releaseYear: Int

    val rentalDuration: Int

    val rentalRate: Double

    val replacementCost: Double

    val specialFeatures: String

    val title: String
  }

  interface FilmActor {
    val actorId: Int

    val filmId: Int

    val lastUpdate: String
  }

  interface FilmCategory {
    val categoryId: Int

    val filmId: Int

    val lastUpdate: String
  }

  interface Inventory {
    val filmId: Int

    val inventoryId: Int

    val lastUpdate: String

    val storeId: Int
  }

  interface Language {
    val languageId: Int

    val lastUpdate: String

    val name: String
  }

  interface Payment {
    val amount: Double

    val customerId: Int

    val paymentDate: String

    val paymentId: Int

    val rentalId: Int

    val staffId: Int
  }

  interface Rental {
    val customerId: Int

    val inventoryId: Int

    val lastUpdate: String

    val rentalDate: Int

    val rentalId: Int

    val returnDate: String

    val staffId: Int
  }

  interface Staff {
    val active: Int

    val addressId: Int

    val email: Int

    val firstName: String

    val lastName: String

    val lastUpdate: String

    val password: String

    val picture: Int

    val staffId: Int

    val storeId: Int

    val username: String
  }

  interface Store {
    val addressId: Int

    val lastUpdate: String

    val managerStaffId: Int

    val storeId: Int
  }
}
