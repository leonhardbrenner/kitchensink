package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface DvdRentalDto {
  @Serializable
  data class actor(
    override val actorId: Int,
    override val firstName: String,
    override val lastName: String,
    override val lastUpdate: String
  ) : DvdRental.actor {
    companion object {
      final val path: String = "/DvdRental/actor"

      final val header: String = "actorId\tfirstName\tlastName\tlastUpdate"


      fun create(source: DvdRental.actor) = actor(source.actorId, source.firstName, source.lastName,
          source.lastUpdate)}
  }

  @Serializable
  data class address(
    override val address: String,
    override val address2: String?,
    override val address_id: Int,
    override val city_id: Int,
    override val district: String?,
    override val last_update: String,
    override val phone: String?,
    override val postal_code: String?
  ) : DvdRental.address {
    companion object {
      final val path: String = "/DvdRental/address"

      final val header: String =
          "address\taddress2\taddress_id\tcity_id\tdistrict\tlast_update\tphone\tpostal_code"


      fun create(source: DvdRental.address) = address(source.address, source.address2,
          source.address_id, source.city_id, source.district, source.last_update, source.phone,
          source.postal_code)}
  }

  @Serializable
  data class category(
    override val category_id: Int,
    override val last_update: String,
    override val name: String
  ) : DvdRental.category {
    companion object {
      final val path: String = "/DvdRental/category"

      final val header: String = "category_id\tlast_update\tname"


      fun create(source: DvdRental.category) = category(source.category_id, source.last_update,
          source.name)}
  }

  @Serializable
  data class city(
    override val city: String,
    override val city_id: Int,
    override val country_id: Int,
    override val last_update: Int
  ) : DvdRental.city {
    companion object {
      final val path: String = "/DvdRental/city"

      final val header: String = "city\tcity_id\tcountry_id\tlast_update"


      fun create(source: DvdRental.city) = city(source.city, source.city_id, source.country_id,
          source.last_update)}
  }

  @Serializable
  data class country(
    override val country: String,
    override val country_id: Int,
    override val last_update: String
  ) : DvdRental.country {
    companion object {
      final val path: String = "/DvdRental/country"

      final val header: String = "country\tcountry_id\tlast_update"


      fun create(source: DvdRental.country) = country(source.country, source.country_id,
          source.last_update)}
  }

  @Serializable
  data class customer(
    override val active: Int,
    override val activebool: Boolean,
    override val address_id: Int,
    override val create_date: String,
    override val customer_id: Int,
    override val email: String,
    override val first_name: String,
    override val last_name: String,
    override val last_update: String,
    override val store_id: Int
  ) : DvdRental.customer {
    companion object {
      final val path: String = "/DvdRental/customer"

      final val header: String =
          "active\tactivebool\taddress_id\tcreate_date\tcustomer_id\temail\tfirst_name\tlast_name\tlast_update\tstore_id"


      fun create(source: DvdRental.customer) = customer(source.active, source.activebool,
          source.address_id, source.create_date, source.customer_id, source.email,
          source.first_name, source.last_name, source.last_update, source.store_id)}
  }

  @Serializable
  data class film(
    override val description: String,
    override val film_id: Int,
    override val fulltext: String,
    override val language_id: Int,
    override val last_update: String,
    override val length: Int,
    override val rating: String,
    override val release_year: Int,
    override val rental_duration: Int,
    override val rental_rate: Double,
    override val replacement_cost: Double,
    override val special_features: String,
    override val title: String
  ) : DvdRental.film {
    companion object {
      final val path: String = "/DvdRental/film"

      final val header: String =
          "description\tfilm_id\tfulltext\tlanguage_id\tlast_update\tlength\trating\trelease_year\trental_duration\trental_rate\treplacement_cost\tspecial_features\ttitle"


      fun create(source: DvdRental.film) = film(source.description, source.film_id, source.fulltext,
          source.language_id, source.last_update, source.length, source.rating, source.release_year,
          source.rental_duration, source.rental_rate, source.replacement_cost,
          source.special_features, source.title)}
  }

  @Serializable
  data class film_actor(
    override val actor_id: Int,
    override val film_id: Int,
    override val last_update: String
  ) : DvdRental.film_actor {
    companion object {
      final val path: String = "/DvdRental/film_actor"

      final val header: String = "actor_id\tfilm_id\tlast_update"


      fun create(source: DvdRental.film_actor) = film_actor(source.actor_id, source.film_id,
          source.last_update)}
  }

  @Serializable
  data class film_category(
    override val category_id: Int,
    override val film_id: Int,
    override val last_update: String
  ) : DvdRental.film_category {
    companion object {
      final val path: String = "/DvdRental/film_category"

      final val header: String = "category_id\tfilm_id\tlast_update"


      fun create(source: DvdRental.film_category) = film_category(source.category_id,
          source.film_id, source.last_update)}
  }

  @Serializable
  data class inventory(
    override val film_id: Int,
    override val inventory_id: Int,
    override val last_update: String,
    override val store_id: Int
  ) : DvdRental.inventory {
    companion object {
      final val path: String = "/DvdRental/inventory"

      final val header: String = "film_id\tinventory_id\tlast_update\tstore_id"


      fun create(source: DvdRental.inventory) = inventory(source.film_id, source.inventory_id,
          source.last_update, source.store_id)}
  }

  @Serializable
  data class language(
    override val language_id: Int,
    override val last_update: String,
    override val name: String
  ) : DvdRental.language {
    companion object {
      final val path: String = "/DvdRental/language"

      final val header: String = "language_id\tlast_update\tname"


      fun create(source: DvdRental.language) = language(source.language_id, source.last_update,
          source.name)}
  }

  @Serializable
  data class payment(
    override val amount: Double,
    override val customer_id: Int,
    override val payment_date: String,
    override val payment_id: Int,
    override val rental_id: Int,
    override val staff_id: Int
  ) : DvdRental.payment {
    companion object {
      final val path: String = "/DvdRental/payment"

      final val header: String =
          "amount\tcustomer_id\tpayment_date\tpayment_id\trental_id\tstaff_id"


      fun create(source: DvdRental.payment) = payment(source.amount, source.customer_id,
          source.payment_date, source.payment_id, source.rental_id, source.staff_id)}
  }

  @Serializable
  data class rental(
    override val customer_id: Int,
    override val inventory_id: Int,
    override val last_update: String,
    override val rental_date: Int,
    override val rental_id: Int,
    override val return_date: String,
    override val staff_id: Int
  ) : DvdRental.rental {
    companion object {
      final val path: String = "/DvdRental/rental"

      final val header: String =
          "customer_id\tinventory_id\tlast_update\trental_date\trental_id\treturn_date\tstaff_id"


      fun create(source: DvdRental.rental) = rental(source.customer_id, source.inventory_id,
          source.last_update, source.rental_date, source.rental_id, source.return_date,
          source.staff_id)}
  }

  @Serializable
  data class staff(
    override val active: Int,
    override val address_id: Int,
    override val email: Int,
    override val first_name: String,
    override val last_name: String,
    override val last_update: String,
    override val password: String,
    override val picture: Int,
    override val staff_id: Int,
    override val store_id: Int,
    override val username: String
  ) : DvdRental.staff {
    companion object {
      final val path: String = "/DvdRental/staff"

      final val header: String =
          "active\taddress_id\temail\tfirst_name\tlast_name\tlast_update\tpassword\tpicture\tstaff_id\tstore_id\tusername"


      fun create(source: DvdRental.staff) = staff(source.active, source.address_id, source.email,
          source.first_name, source.last_name, source.last_update, source.password, source.picture,
          source.staff_id, source.store_id, source.username)}
  }

  @Serializable
  data class store(
    override val address_id: Int,
    override val last_update: String,
    override val manager_staff_id: Int,
    override val store_id: Int
  ) : DvdRental.store {
    companion object {
      final val path: String = "/DvdRental/store"

      final val header: String = "address_id\tlast_update\tmanager_staff_id\tstore_id"


      fun create(source: DvdRental.store) = store(source.address_id, source.last_update,
          source.manager_staff_id, source.store_id)}
  }
}
