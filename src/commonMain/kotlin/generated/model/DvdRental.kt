package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String

interface DvdRental {
  interface actor {
    val actor_id: Int

    val first_name: String

    val last_name: String

    val last_update: String
  }

  interface address {
    val address: String

    val address2: String?

    val address_id: Int

    val city_id: Int

    val district: String?

    val last_update: String

    val phone: String?

    val postal_code: String?
  }

  interface category {
    val category_id: Int

    val last_update: String

    val name: String
  }

  interface city {
    val city: String

    val city_id: Int

    val country_id: Int

    val last_update: Int
  }

  interface country {
    val country: String

    val country_id: Int

    val last_update: String
  }

  interface customer {
    val active: Int

    val activebool: Boolean

    val address_id: Int

    val create_date: String

    val customer_id: Int

    val email: String

    val first_name: String

    val last_name: String

    val last_update: String

    val store_id: Int
  }

  interface film {
    val description: String

    val film_id: Int

    val fulltext: String

    val language_id: Int

    val last_update: String

    val length: Int

    val rating: String

    val release_year: Int

    val rental_duration: Int

    val rental_rate: Double

    val replacement_cost: Double

    val special_features: String

    val title: String
  }

  interface film_actor {
    val actor_id: Int

    val film_id: Int

    val last_update: String
  }

  interface film_category {
    val category_id: Int

    val film_id: Int

    val last_update: String
  }

  interface inventory {
    val film_id: Int

    val inventory_id: Int

    val last_update: String

    val store_id: Int
  }

  interface language {
    val language_id: Int

    val last_update: String

    val name: String
  }

  interface payment {
    val amount: Double

    val customer_id: Int

    val payment_date: String

    val payment_id: Int

    val rental_id: Int

    val staff_id: Int
  }

  interface rental {
    val customer_id: Int

    val inventory_id: Int

    val last_update: String

    val rental_date: Int

    val rental_id: Int

    val return_date: String

    val staff_id: Int
  }

  interface staff {
    val active: Int

    val address_id: Int

    val email: Int

    val first_name: String

    val last_name: String

    val last_update: String

    val password: String

    val picture: Int

    val staff_id: Int

    val store_id: Int

    val username: String
  }

  interface store {
    val address_id: Int

    val last_update: String

    val manager_staff_id: Int

    val store_id: Int
  }
}
