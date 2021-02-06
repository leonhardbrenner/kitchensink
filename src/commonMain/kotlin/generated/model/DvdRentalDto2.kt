package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface DvdRentalDto2 {
  @Serializable
  data class actor : DvdRental.actor {
    override val actor_id: Int = actor_id

    override val first_name: String = first_name

    override val last_name: String = last_name

    override val last_update: String = last_update

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.actor) = actor(source.actor_id, source.first_name,
          source.last_name, source.last_update)}
  }

  @Serializable
  data class address : DvdRental.address {
    override val address: String = address

    override val address2: String? = address2

    override val address_id: Int = address_id

    override val city_id: Int = city_id

    override val district: String? = district

    override val last_update: String = last_update

    override val phone: String? = phone

    override val postal_code: String? = postal_code

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.address) = address(source.address, source.address2,
          source.address_id, source.city_id, source.district, source.last_update, source.phone,
          source.postal_code)}
  }

  @Serializable
  data class category : DvdRental.category {
    override val category_id: Int = category_id

    override val last_update: String = last_update

    override val name: String = name

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.category) = category(source.category_id, source.last_update,
          source.name)}
  }

  @Serializable
  data class city : DvdRental.city {
    override val city: String = city

    override val city_id: Int = city_id

    override val country_id: Int = country_id

    override val last_update: Int = last_update

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.city) = city(source.city, source.city_id, source.country_id,
          source.last_update)}
  }

  @Serializable
  data class country : DvdRental.country {
    override val country: String = country

    override val country_id: Int = country_id

    override val last_update: String = last_update

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.country) = country(source.country, source.country_id,
          source.last_update)}
  }

  @Serializable
  data class customer : DvdRental.customer {
    override val active: Int = active

    override val activebool: Boolean = activebool

    override val address_id: Int = address_id

    override val create_date: String = create_date

    override val customer_id: Int = customer_id

    override val email: String = email

    override val first_name: String = first_name

    override val last_name: String = last_name

    override val last_update: String = last_update

    override val store_id: Int = store_id

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.customer) = customer(source.active, source.activebool,
          source.address_id, source.create_date, source.customer_id, source.email,
          source.first_name, source.last_name, source.last_update, source.store_id)}
  }

  @Serializable
  data class film : DvdRental.film {
    override val description: String = description

    override val film_id: Int = film_id

    override val fulltext: String = fulltext

    override val language_id: Int = language_id

    override val last_update: String = last_update

    override val length: Int = length

    override val rating: String = rating

    override val release_year: Int = release_year

    override val rental_duration: Int = rental_duration

    override val rental_rate: Double = rental_rate

    override val replacement_cost: Double = replacement_cost

    override val special_features: String = special_features

    override val title: String = title

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.film) = film(source.description, source.film_id, source.fulltext,
          source.language_id, source.last_update, source.length, source.rating, source.release_year,
          source.rental_duration, source.rental_rate, source.replacement_cost,
          source.special_features, source.title)}
  }

  @Serializable
  data class film_actor : DvdRental.film_actor {
    override val actor_id: Int = actor_id

    override val film_id: Int = film_id

    override val last_update: String = last_update

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.film_actor) = film_actor(source.actor_id, source.film_id,
          source.last_update)}
  }

  @Serializable
  data class film_category : DvdRental.film_category {
    override val category_id: Int = category_id

    override val film_id: Int = film_id

    override val last_update: String = last_update

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.film_category) = film_category(source.category_id,
          source.film_id, source.last_update)}
  }

  @Serializable
  data class inventory : DvdRental.inventory {
    override val film_id: Int = film_id

    override val inventory_id: Int = inventory_id

    override val last_update: String = last_update

    override val store_id: Int = store_id

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.inventory) = inventory(source.film_id, source.inventory_id,
          source.last_update, source.store_id)}
  }

  @Serializable
  data class language : DvdRental.language {
    override val language_id: Int = language_id

    override val last_update: String = last_update

    override val name: String = name

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.language) = language(source.language_id, source.last_update,
          source.name)}
  }

  @Serializable
  data class payment : DvdRental.payment {
    override val amount: Double = amount

    override val customer_id: Int = customer_id

    override val payment_date: String = payment_date

    override val payment_id: Int = payment_id

    override val rental_id: Int = rental_id

    override val staff_id: Int = staff_id

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.payment) = payment(source.amount, source.customer_id,
          source.payment_date, source.payment_id, source.rental_id, source.staff_id)}
  }

  @Serializable
  data class rental : DvdRental.rental {
    override val customer_id: Int = customer_id

    override val inventory_id: Int = inventory_id

    override val last_update: String = last_update

    override val rental_date: Int = rental_date

    override val rental_id: Int = rental_id

    override val return_date: String = return_date

    override val staff_id: Int = staff_id

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.rental) = rental(source.customer_id, source.inventory_id,
          source.last_update, source.rental_date, source.rental_id, source.return_date,
          source.staff_id)}
  }

  @Serializable
  data class staff : DvdRental.staff {
    override val active: Int = active

    override val address_id: Int = address_id

    override val email: Int = email

    override val first_name: String = first_name

    override val last_name: String = last_name

    override val last_update: String = last_update

    override val password: String = password

    override val picture: Int = picture

    override val staff_id: Int = staff_id

    override val store_id: Int = store_id

    override val username: String = username

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.staff) = staff(source.active, source.address_id, source.email,
          source.first_name, source.last_name, source.last_update, source.password, source.picture,
          source.staff_id, source.store_id, source.username)}
  }

  @Serializable
  data class store : DvdRental.store {
    override val address_id: Int = address_id

    override val last_update: String = last_update

    override val manager_staff_id: Int = manager_staff_id

    override val store_id: Int = store_id

    companion object {
      final val path: String = ""

      fun create(source: DvdRental.store) = store(source.address_id, source.last_update,
          source.manager_staff_id, source.store_id)}
  }
}
