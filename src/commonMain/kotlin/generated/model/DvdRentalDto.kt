package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface DvdRentalDto {
  @Serializable
  data class Actor(
    override val actorId: Int,
    override val firstName: String,
    override val lastName: String,
    override val lastUpdate: String
  ) : DvdRental.Actor {
    companion object {
      const val path: String = "/DvdRental/Actor"

      fun create(source: DvdRental.Actor) = DvdRentalDto.Actor(source.actorId, source.firstName,
          source.lastName, source.lastUpdate)}
  }

  @Serializable
  data class Address(
    override val addressId: Int,
    override val address: String,
    override val address2: String?,
    override val district: String?,
    override val cityId: Int,
    override val postalCode: String?,
    override val phone: String?,
    override val lastUpdate: String
  ) : DvdRental.Address {
    companion object {
      const val path: String = "/DvdRental/Address"

      fun create(source: DvdRental.Address) = DvdRentalDto.Address(source.addressId, source.address,
          source.address2, source.district, source.cityId, source.postalCode, source.phone,
          source.lastUpdate)}
  }

  @Serializable
  data class Category(
    override val categoryId: Int,
    override val name: String,
    override val lastUpdate: String
  ) : DvdRental.Category {
    companion object {
      const val path: String = "/DvdRental/Category"

      fun create(source: DvdRental.Category) = DvdRentalDto.Category(source.categoryId, source.name,
          source.lastUpdate)}
  }

  @Serializable
  data class City(
    override val cityId: Int,
    override val city: String,
    override val countryId: Int,
    override val lastUpdate: String
  ) : DvdRental.City {
    companion object {
      const val path: String = "/DvdRental/City"

      fun create(source: DvdRental.City) = DvdRentalDto.City(source.cityId, source.city,
          source.countryId, source.lastUpdate)}
  }

  @Serializable
  data class Country(
    override val countryId: Int,
    override val country: String,
    override val lastUpdate: String
  ) : DvdRental.Country {
    companion object {
      const val path: String = "/DvdRental/Country"

      fun create(source: DvdRental.Country) = DvdRentalDto.Country(source.countryId, source.country,
          source.lastUpdate)}
  }

  @Serializable
  data class Customer(
    override val customerId: Int,
    override val storeId: Int,
    override val firstName: String,
    override val lastName: String,
    override val email: String,
    override val addressId: Int,
    override val activebool: Boolean,
    override val createDate: String,
    override val lastUpdate: String,
    override val active: Int
  ) : DvdRental.Customer {
    companion object {
      const val path: String = "/DvdRental/Customer"

      fun create(source: DvdRental.Customer) = DvdRentalDto.Customer(source.customerId,
          source.storeId, source.firstName, source.lastName, source.email, source.addressId,
          source.activebool, source.createDate, source.lastUpdate, source.active)}
  }

  @Serializable
  data class Film(
    override val filmId: Int,
    override val title: String,
    override val description: String,
    override val releaseYear: Int,
    override val languageId: Int,
    override val rentalDuration: Int,
    override val rentalRate: Double,
    override val length: Int,
    override val replacementCost: Double,
    override val rating: String,
    override val lastUpdate: String,
    override val specialFeatures: String,
    override val fullText: String
  ) : DvdRental.Film {
    companion object {
      const val path: String = "/DvdRental/Film"

      fun create(source: DvdRental.Film) = DvdRentalDto.Film(source.filmId, source.title,
          source.description, source.releaseYear, source.languageId, source.rentalDuration,
          source.rentalRate, source.length, source.replacementCost, source.rating,
          source.lastUpdate, source.specialFeatures, source.fullText)}
  }

  @Serializable
  data class FilmActor(
    override val actorId: Int,
    override val filmId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmActor {
    companion object {
      const val path: String = "/DvdRental/FilmActor"

      fun create(source: DvdRental.FilmActor) = DvdRentalDto.FilmActor(source.actorId,
          source.filmId, source.lastUpdate)}
  }

  @Serializable
  data class FilmCategory(
    override val filmId: Int,
    override val categoryId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmCategory {
    companion object {
      const val path: String = "/DvdRental/FilmCategory"

      fun create(source: DvdRental.FilmCategory) = DvdRentalDto.FilmCategory(source.filmId,
          source.categoryId, source.lastUpdate)}
  }

  @Serializable
  data class Inventory(
    override val inventoryId: Int,
    override val filmId: Int,
    override val storeId: Int,
    override val lastUpdate: String
  ) : DvdRental.Inventory {
    companion object {
      const val path: String = "/DvdRental/Inventory"

      fun create(source: DvdRental.Inventory) = DvdRentalDto.Inventory(source.inventoryId,
          source.filmId, source.storeId, source.lastUpdate)}
  }

  @Serializable
  data class Language(
    override val languageId: Int,
    override val name: String,
    override val lastUpdate: String
  ) : DvdRental.Language {
    companion object {
      const val path: String = "/DvdRental/Language"

      fun create(source: DvdRental.Language) = DvdRentalDto.Language(source.languageId, source.name,
          source.lastUpdate)}
  }

  @Serializable
  data class Payment(
    override val paymentId: Int,
    override val customerId: Int,
    override val staffId: Int,
    override val rentalId: Int,
    override val amount: Double,
    override val paymentDate: String
  ) : DvdRental.Payment {
    companion object {
      const val path: String = "/DvdRental/Payment"

      fun create(source: DvdRental.Payment) = DvdRentalDto.Payment(source.paymentId,
          source.customerId, source.staffId, source.rentalId, source.amount, source.paymentDate)}
  }

  @Serializable
  data class Rental(
    override val rentalId: Int,
    override val rentalDate: String,
    override val inventoryId: Int,
    override val customerId: Int,
    override val returnDate: String,
    override val staffId: Int,
    override val lastUpdate: String
  ) : DvdRental.Rental {
    companion object {
      const val path: String = "/DvdRental/Rental"

      fun create(source: DvdRental.Rental) = DvdRentalDto.Rental(source.rentalId, source.rentalDate,
          source.inventoryId, source.customerId, source.returnDate, source.staffId,
          source.lastUpdate)}
  }

  @Serializable
  data class Staff(
    override val staffId: Int,
    override val firstName: String,
    override val lastName: String,
    override val addressId: Int,
    override val email: String,
    override val storeId: Int,
    override val active: String,
    override val username: String,
    override val password: String,
    override val lastUpdate: String,
    override val picture: String
  ) : DvdRental.Staff {
    companion object {
      const val path: String = "/DvdRental/Staff"

      fun create(source: DvdRental.Staff) = DvdRentalDto.Staff(source.staffId, source.firstName,
          source.lastName, source.addressId, source.email, source.storeId, source.active,
          source.username, source.password, source.lastUpdate, source.picture)}
  }

  @Serializable
  data class Store(
    override val storeId: Int,
    override val managerStaffId: Int,
    override val addressId: Int,
    override val lastUpdate: String
  ) : DvdRental.Store {
    companion object {
      const val path: String = "/DvdRental/Store"

      fun create(source: DvdRental.Store) = DvdRentalDto.Store(source.storeId,
          source.managerStaffId, source.addressId, source.lastUpdate)}
  }
}
