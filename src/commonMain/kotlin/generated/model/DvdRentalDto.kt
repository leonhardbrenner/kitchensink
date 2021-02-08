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
      val path: String = "/DvdRental/Actor"

      fun create(source: DvdRental.Actor) = Actor(source.actorId, source.firstName, source.lastName,
          source.lastUpdate)}
  }

  @Serializable
  data class Address(
    override val address: String,
    override val address2: String?,
    override val addressId: Int,
    override val cityId: Int,
    override val district: String?,
    override val lastUpdate: String,
    override val phone: String?,
    override val postalCode: String?
  ) : DvdRental.Address {
    companion object {
      val path: String = "/DvdRental/Address"

      fun create(source: DvdRental.Address) = Address(source.address, source.address2,
          source.addressId, source.cityId, source.district, source.lastUpdate, source.phone,
          source.postalCode)}
  }

  @Serializable
  data class Category(
    override val categoryId: Int,
    override val lastUpdate: String,
    override val name: String
  ) : DvdRental.Category {
    companion object {
      val path: String = "/DvdRental/Category"

      fun create(source: DvdRental.Category) = Category(source.categoryId, source.lastUpdate,
          source.name)}
  }

  @Serializable
  data class City(
    override val city: String,
    override val cityId: Int,
    override val countryId: Int,
    override val lastUpdate: Int
  ) : DvdRental.City {
    companion object {
      val path: String = "/DvdRental/City"

      fun create(source: DvdRental.City) = City(source.city, source.cityId, source.countryId,
          source.lastUpdate)}
  }

  @Serializable
  data class Country(
    override val country: String,
    override val countryId: Int,
    override val lastUpdate: String
  ) : DvdRental.Country {
    companion object {
      val path: String = "/DvdRental/Country"

      fun create(source: DvdRental.Country) = Country(source.country, source.countryId,
          source.lastUpdate)}
  }

  @Serializable
  data class Customer(
    override val active: Int,
    override val activebool: Boolean,
    override val addressId: Int,
    override val createDate: String,
    override val customerId: Int,
    override val email: String,
    override val firstName: String,
    override val lastName: String,
    override val lastUpdate: String,
    override val storeId: Int
  ) : DvdRental.Customer {
    companion object {
      val path: String = "/DvdRental/Customer"

      fun create(source: DvdRental.Customer) = Customer(source.active, source.activebool,
          source.addressId, source.createDate, source.customerId, source.email, source.firstName,
          source.lastName, source.lastUpdate, source.storeId)}
  }

  @Serializable
  data class Film(
    override val description: String,
    override val filmId: Int,
    override val fullText: String,
    override val languageId: Int,
    override val lastUpdate: String,
    override val length: Int,
    override val rating: String,
    override val releaseYear: Int,
    override val rentalDuration: Int,
    override val rentalRate: Double,
    override val replacementCost: Double,
    override val specialFeatures: String,
    override val title: String
  ) : DvdRental.Film {
    companion object {
      val path: String = "/DvdRental/Film"

      fun create(source: DvdRental.Film) = Film(source.description, source.filmId, source.fullText,
          source.languageId, source.lastUpdate, source.length, source.rating, source.releaseYear,
          source.rentalDuration, source.rentalRate, source.replacementCost, source.specialFeatures,
          source.title)}
  }

  @Serializable
  data class FilmActor(
    override val actorId: Int,
    override val filmId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmActor {
    companion object {
      val path: String = "/DvdRental/FilmActor"

      fun create(source: DvdRental.FilmActor) = FilmActor(source.actorId, source.filmId,
          source.lastUpdate)}
  }

  @Serializable
  data class FilmCategory(
    override val categoryId: Int,
    override val filmId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmCategory {
    companion object {
      val path: String = "/DvdRental/FilmCategory"

      fun create(source: DvdRental.FilmCategory) = FilmCategory(source.categoryId, source.filmId,
          source.lastUpdate)}
  }

  @Serializable
  data class Inventory(
    override val filmId: Int,
    override val inventoryId: Int,
    override val lastUpdate: String,
    override val storeId: Int
  ) : DvdRental.Inventory {
    companion object {
      val path: String = "/DvdRental/Inventory"

      fun create(source: DvdRental.Inventory) = Inventory(source.filmId, source.inventoryId,
          source.lastUpdate, source.storeId)}
  }

  @Serializable
  data class Language(
    override val languageId: Int,
    override val lastUpdate: String,
    override val name: String
  ) : DvdRental.Language {
    companion object {
      val path: String = "/DvdRental/Language"

      fun create(source: DvdRental.Language) = Language(source.languageId, source.lastUpdate,
          source.name)}
  }

  @Serializable
  data class Payment(
    override val amount: Double,
    override val customerId: Int,
    override val paymentDate: String,
    override val paymentId: Int,
    override val rentalId: Int,
    override val staffId: Int
  ) : DvdRental.Payment {
    companion object {
      val path: String = "/DvdRental/Payment"

      fun create(source: DvdRental.Payment) = Payment(source.amount, source.customerId,
          source.paymentDate, source.paymentId, source.rentalId, source.staffId)}
  }

  @Serializable
  data class Rental(
    override val customerId: Int,
    override val inventoryId: Int,
    override val lastUpdate: String,
    override val rentalDate: Int,
    override val rentalId: Int,
    override val returnDate: String,
    override val staffId: Int
  ) : DvdRental.Rental {
    companion object {
      val path: String = "/DvdRental/Rental"

      fun create(source: DvdRental.Rental) = Rental(source.customerId, source.inventoryId,
          source.lastUpdate, source.rentalDate, source.rentalId, source.returnDate, source.staffId)}
  }

  @Serializable
  data class Staff(
    override val active: Int,
    override val addressId: Int,
    override val email: Int,
    override val firstName: String,
    override val lastName: String,
    override val lastUpdate: String,
    override val password: String,
    override val picture: Int,
    override val staffId: Int,
    override val storeId: Int,
    override val username: String
  ) : DvdRental.Staff {
    companion object {
      val path: String = "/DvdRental/Staff"

      fun create(source: DvdRental.Staff) = Staff(source.active, source.addressId, source.email,
          source.firstName, source.lastName, source.lastUpdate, source.password, source.picture,
          source.staffId, source.storeId, source.username)}
  }

  @Serializable
  data class Store(
    override val addressId: Int,
    override val lastUpdate: String,
    override val managerStaffId: Int,
    override val storeId: Int
  ) : DvdRental.Store {
    companion object {
      val path: String = "/DvdRental/Store"

      fun create(source: DvdRental.Store) = Store(source.addressId, source.lastUpdate,
          source.managerStaffId, source.storeId)}
  }
}
