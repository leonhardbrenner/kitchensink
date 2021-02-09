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
    val addressId: Int

    val address: String

    val address2: String?

    val district: String?

    val cityId: Int

    val postalCode: String?

    val phone: String?

    val lastUpdate: String
  }

  interface Category {
    val categoryId: Int

    val name: String

    val lastUpdate: String
  }

  interface City {
    val cityId: Int

    val city: String

    val countryId: Int

    val lastUpdate: String
  }

  interface Country {
    val countryId: Int

    val country: String

    val lastUpdate: String
  }

  interface Customer {
    val customerId: Int

    val storeId: Int

    val firstName: String

    val lastName: String

    val email: String

    val addressId: Int

    val activebool: Boolean

    val createDate: String

    val lastUpdate: String

    val active: Int
  }

  interface Film {
    val filmId: Int

    val title: String

    val description: String

    val releaseYear: Int

    val languageId: Int

    val rentalDuration: Int

    val rentalRate: Double

    val length: Int

    val replacementCost: Double

    val rating: String

    val lastUpdate: String

    val specialFeatures: String

    val fullText: String
  }

  interface FilmActor {
    val actorId: Int

    val filmId: Int

    val lastUpdate: String
  }

  interface FilmCategory {
    val filmId: Int

    val categoryId: Int

    val lastUpdate: String
  }

  interface Inventory {
    val inventoryId: Int

    val filmId: Int

    val storeId: Int

    val lastUpdate: String
  }

  interface Language {
    val languageId: Int

    val name: String

    val lastUpdate: String
  }

  interface Payment {
    val paymentId: Int

    val customerId: Int

    val staffId: Int

    val rentalId: Int

    val amount: Double

    val paymentDate: String
  }

  interface Rental {
    val rentalId: Int

    val rentalDate: String

    val inventoryId: Int

    val customerId: Int

    val returnDate: String

    val staffId: Int

    val lastUpdate: String
  }

  interface Staff {
    val staffId: Int

    val firstName: String

    val lastName: String

    val addressId: Int

    val email: String

    val storeId: Int

    val active: String

    val username: String

    val password: String

    val lastUpdate: String

    val picture: String
  }

  interface Store {
    val storeId: Int

    val managerStaffId: Int

    val addressId: Int

    val lastUpdate: String
  }
}
