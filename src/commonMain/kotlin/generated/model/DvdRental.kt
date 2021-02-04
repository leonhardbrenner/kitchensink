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
    val address_id: Int

    val address: String

    val address2: String?

    val district: String?

    val city_id: Int

    val postal_code: String?

    val phone: String?

    val last_update: String
  }

  interface category {
    val category_id: Int

    val name: String

    val last_update: String
  }

  interface city {
    val city_id: Int

    val city: String

    val country_id: Int

    val last_update: Int
  }

  interface country {
    val country_id: Int

    val country: String

    val last_update: String
  }

  interface customer {
    val customer_id: Int

    val store_id: Int

    val first_name: String

    val last_name: String

    val email: String

    val address_id: Int

    val activebool: Boolean

    val create_date: String

    val last_update: String

    val active: Int
  }

  interface film {
    val film_id: Int

    val title: String

    val description: String

    val release_year: Int

    val language_id: Int

    val rental_duration: Int

    val rental_rate: Double

    val length: Int

    val replacement_cost: Double

    val rating: String

    val last_update: String

    val special_features: String

    val fulltext: String
  }

  interface film_actor {
    val actor_id: Int

    val film_id: Int

    val last_update: String
  }

  interface film_category {
    val film_id: Int

    val category_id: Int

    val last_update: String
  }

  interface inventory {
    val inventory_id: Int

    val film_id: Int

    val store_id: Int

    val last_update: String
  }

  interface language {
    val language_id: Int

    val name: String

    val last_update: String
  }

  interface payment {
    val payment_id: Int

    val customer_id: Int

    val staff_id: Int

    val rental_id: Int

    val amount: Double

    val payment_date: String
  }

  interface rental {
    val rental_id: Int

    val rental_date: Int

    val inventory_id: Int

    val customer_id: Int

    val return_date: String

    val staff_id: Int

    val last_update: String
  }

  interface staff {
    val staff_id: Int

    val first_name: Int

    val last_name: Int

    val address_id: Int

    val email: Int

    val store_id: Int

    val active: Int

    val username: String

    val password: String

    val last_update: String

    val picture: Int
  }

  interface store {
    val store_id: Int

    val manager_staff_id: Int

    val address_id: Int

    val last_update: String
  }
}
