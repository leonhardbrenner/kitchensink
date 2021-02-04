package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String

interface DvdRentalCsvLoader {
  data class actor(
    override val actor_id: Int,
    override val first_name: String,
    override val last_name: String,
    override val last_update: String
  ) : DvdRental.actor {
    companion object {
      final val path: String = "/DvdRental/actor"

      final val header: String = "actor_id\tfirst_name\tlast_name\tlast_update"


      fun create(source: DvdRental.actor) = actor(source.actor_id, source.first_name,
          source.last_name, source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<actor>(pathname, header)}
  }

  data class address(
    override val address_id: Int,
    override val address: String,
    override val address2: String?,
    override val district: String?,
    override val city_id: Int,
    override val postal_code: String?,
    override val phone: String?,
    override val last_update: String
  ) : DvdRental.address {
    companion object {
      final val path: String = "/DvdRental/address"

      final val header: String =
          "address_id\taddress\taddress2\tdistrict\tcity_id\tpostal_code\tphone\tlast_update"


      fun create(source: DvdRental.address) = address(source.address_id, source.address,
          source.address2, source.district, source.city_id, source.postal_code, source.phone,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<address>(pathname, header)}
  }

  data class category(
    override val category_id: Int,
    override val name: String,
    override val last_update: String
  ) : DvdRental.category {
    companion object {
      final val path: String = "/DvdRental/category"

      final val header: String = "category_id\tname\tlast_update"


      fun create(source: DvdRental.category) = category(source.category_id, source.name,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<category>(pathname, header)}
  }

  data class city(
    override val city_id: Int,
    override val city: String,
    override val country_id: Int,
    override val last_update: Int
  ) : DvdRental.city {
    companion object {
      final val path: String = "/DvdRental/city"

      final val header: String = "city_id\tcity\tcountry_id\tlast_update"


      fun create(source: DvdRental.city) = city(source.city_id, source.city, source.country_id,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<city>(pathname, header)}
  }

  data class country(
    override val country_id: Int,
    override val country: String,
    override val last_update: String
  ) : DvdRental.country {
    companion object {
      final val path: String = "/DvdRental/country"

      final val header: String = "country_id\tcountry\tlast_update"


      fun create(source: DvdRental.country) = country(source.country_id, source.country,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<country>(pathname, header)}
  }

  data class customer(
    override val customer_id: Int,
    override val store_id: Int,
    override val first_name: String,
    override val last_name: String,
    override val email: String,
    override val address_id: Int,
    override val activebool: Boolean,
    override val create_date: String,
    override val last_update: String,
    override val active: Int
  ) : DvdRental.customer {
    companion object {
      final val path: String = "/DvdRental/customer"

      final val header: String =
          "customer_id\tstore_id\tfirst_name\tlast_name\temail\taddress_id\tactivebool\tcreate_date\tlast_update\tactive"


      fun create(source: DvdRental.customer) = customer(source.customer_id, source.store_id,
          source.first_name, source.last_name, source.email, source.address_id, source.activebool,
          source.create_date, source.last_update, source.active)
      fun loadCsv(pathname: String) = model.loadCsv<customer>(pathname, header)}
  }

  data class film(
    override val film_id: Int,
    override val title: String,
    override val description: String,
    override val release_year: Int,
    override val language_id: Int,
    override val rental_duration: Int,
    override val rental_rate: Double,
    override val length: Int,
    override val replacement_cost: Double,
    override val rating: String,
    override val last_update: String,
    override val special_features: String,
    override val fulltext: String
  ) : DvdRental.film {
    companion object {
      final val path: String = "/DvdRental/film"

      final val header: String =
          "film_id\ttitle\tdescription\trelease_year\tlanguage_id\trental_duration\trental_rate\tlength\treplacement_cost\trating\tlast_update\tspecial_features\tfulltext"


      fun create(source: DvdRental.film) = film(source.film_id, source.title, source.description,
          source.release_year, source.language_id, source.rental_duration, source.rental_rate,
          source.length, source.replacement_cost, source.rating, source.last_update,
          source.special_features, source.fulltext)
      fun loadCsv(pathname: String) = model.loadCsv<film>(pathname, header)}
  }

  data class film_actor(
    override val actor_id: Int,
    override val film_id: Int,
    override val last_update: String
  ) : DvdRental.film_actor {
    companion object {
      final val path: String = "/DvdRental/film_actor"

      final val header: String = "actor_id\tfilm_id\tlast_update"


      fun create(source: DvdRental.film_actor) = film_actor(source.actor_id, source.film_id,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<film_actor>(pathname, header)}
  }

  data class film_category(
    override val film_id: Int,
    override val category_id: Int,
    override val last_update: String
  ) : DvdRental.film_category {
    companion object {
      final val path: String = "/DvdRental/film_category"

      final val header: String = "film_id\tcategory_id\tlast_update"


      fun create(source: DvdRental.film_category) = film_category(source.film_id,
          source.category_id, source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<film_category>(pathname, header)}
  }

  data class inventory(
    override val inventory_id: Int,
    override val film_id: Int,
    override val store_id: Int,
    override val last_update: String
  ) : DvdRental.inventory {
    companion object {
      final val path: String = "/DvdRental/inventory"

      final val header: String = "inventory_id\tfilm_id\tstore_id\tlast_update"


      fun create(source: DvdRental.inventory) = inventory(source.inventory_id, source.film_id,
          source.store_id, source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<inventory>(pathname, header)}
  }

  data class language(
    override val language_id: Int,
    override val name: String,
    override val last_update: String
  ) : DvdRental.language {
    companion object {
      final val path: String = "/DvdRental/language"

      final val header: String = "language_id\tname\tlast_update"


      fun create(source: DvdRental.language) = language(source.language_id, source.name,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<language>(pathname, header)}
  }

  data class payment(
    override val payment_id: Int,
    override val customer_id: Int,
    override val staff_id: Int,
    override val rental_id: Int,
    override val amount: Double,
    override val payment_date: String
  ) : DvdRental.payment {
    companion object {
      final val path: String = "/DvdRental/payment"

      final val header: String =
          "payment_id\tcustomer_id\tstaff_id\trental_id\tamount\tpayment_date"


      fun create(source: DvdRental.payment) = payment(source.payment_id, source.customer_id,
          source.staff_id, source.rental_id, source.amount, source.payment_date)
      fun loadCsv(pathname: String) = model.loadCsv<payment>(pathname, header)}
  }

  data class rental(
    override val rental_id: Int,
    override val rental_date: Int,
    override val inventory_id: Int,
    override val customer_id: Int,
    override val return_date: String,
    override val staff_id: Int,
    override val last_update: String
  ) : DvdRental.rental {
    companion object {
      final val path: String = "/DvdRental/rental"

      final val header: String =
          "rental_id\trental_date\tinventory_id\tcustomer_id\treturn_date\tstaff_id\tlast_update"


      fun create(source: DvdRental.rental) = rental(source.rental_id, source.rental_date,
          source.inventory_id, source.customer_id, source.return_date, source.staff_id,
          source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<rental>(pathname, header)}
  }

  data class staff(
    override val staff_id: Int,
    override val first_name: Int,
    override val last_name: Int,
    override val address_id: Int,
    override val email: Int,
    override val store_id: Int,
    override val active: Int,
    override val username: String,
    override val password: String,
    override val last_update: String,
    override val picture: Int
  ) : DvdRental.staff {
    companion object {
      final val path: String = "/DvdRental/staff"

      final val header: String =
          "staff_id\tfirst_name\tlast_name\taddress_id\temail\tstore_id\tactive\tusername\tpassword\tlast_update\tpicture"


      fun create(source: DvdRental.staff) = staff(source.staff_id, source.first_name,
          source.last_name, source.address_id, source.email, source.store_id, source.active,
          source.username, source.password, source.last_update, source.picture)
      fun loadCsv(pathname: String) = model.loadCsv<staff>(pathname, header)}
  }

  data class store(
    override val store_id: Int,
    override val manager_staff_id: Int,
    override val address_id: Int,
    override val last_update: String
  ) : DvdRental.store {
    companion object {
      final val path: String = "/DvdRental/store"

      final val header: String = "store_id\tmanager_staff_id\taddress_id\tlast_update"


      fun create(source: DvdRental.store) = store(source.store_id, source.manager_staff_id,
          source.address_id, source.last_update)
      fun loadCsv(pathname: String) = model.loadCsv<store>(pathname, header)}
  }
}
