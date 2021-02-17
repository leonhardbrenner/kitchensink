package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface DvdRentalDto {
  @Serializable
  data class Actor(
    override val actorId: kotlin.Int,
    override val firstName: kotlin.String,
    override val lastName: kotlin.String,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Actor {
    companion object {
      val path: String = "/DvdRental/Actor"

      fun create(source: DvdRental.Actor) = DvdRentalDto.Actor(source.actorId, source.firstName,
          source.lastName, source.lastUpdate)}
  }

  @Serializable
  data class Address(
    override val addressId: kotlin.Int,
    override val address: kotlin.String,
    override val address2: kotlin.String?,
    override val district: kotlin.String?,
    override val cityId: kotlin.Int,
    override val postalCode: kotlin.String?,
    override val phone: kotlin.String?,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Address {
    companion object {
      val path: String = "/DvdRental/Address"

      fun create(source: DvdRental.Address) = DvdRentalDto.Address(source.addressId, source.address,
          source.address2, source.district, source.cityId, source.postalCode, source.phone,
          source.lastUpdate)}
  }

  @Serializable
  data class Category(
    override val categoryId: kotlin.Int,
    override val name: kotlin.String,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Category {
    companion object {
      val path: String = "/DvdRental/Category"

      fun create(source: DvdRental.Category) = DvdRentalDto.Category(source.categoryId, source.name,
          source.lastUpdate)}
  }

  @Serializable
  data class City(
    override val cityId: kotlin.Int,
    override val city: kotlin.String,
    override val countryId: kotlin.Int,
    override val lastUpdate: kotlin.String
  ) : DvdRental.City {
    companion object {
      val path: String = "/DvdRental/City"

      fun create(source: DvdRental.City) = DvdRentalDto.City(source.cityId, source.city,
          source.countryId, source.lastUpdate)}
  }

  @Serializable
  data class Country(
    override val countryId: kotlin.Int,
    override val country: kotlin.String,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Country {
    companion object {
      val path: String = "/DvdRental/Country"

      fun create(source: DvdRental.Country) = DvdRentalDto.Country(source.countryId, source.country,
          source.lastUpdate)}
  }

  @Serializable
  data class Customer(
    override val customerId: kotlin.Int,
    override val storeId: kotlin.Int,
    override val firstName: kotlin.String,
    override val lastName: kotlin.String,
    override val email: kotlin.String,
    override val addressId: kotlin.Int,
    override val activebool: kotlin.Boolean,
    override val createDate: kotlin.String,
    override val lastUpdate: kotlin.String,
    override val active: kotlin.Int
  ) : DvdRental.Customer {
    companion object {
      val path: String = "/DvdRental/Customer"

      fun create(source: DvdRental.Customer) = DvdRentalDto.Customer(source.customerId,
          source.storeId, source.firstName, source.lastName, source.email, source.addressId,
          source.activebool, source.createDate, source.lastUpdate, source.active)}
  }

  @Serializable
  data class Film(
    override val filmId: kotlin.Int,
    override val title: kotlin.String,
    override val description: kotlin.String,
    override val releaseYear: kotlin.Int,
    override val languageId: kotlin.Int,
    override val rentalDuration: kotlin.Int,
    override val rentalRate: kotlin.Double,
    override val length: kotlin.Int,
    override val replacementCost: kotlin.Double,
    override val rating: kotlin.String,
    override val lastUpdate: kotlin.String,
    override val specialFeatures: kotlin.String,
    override val fullText: kotlin.String
  ) : DvdRental.Film {
    companion object {
      val path: String = "/DvdRental/Film"

      fun create(source: DvdRental.Film) = DvdRentalDto.Film(source.filmId, source.title,
          source.description, source.releaseYear, source.languageId, source.rentalDuration,
          source.rentalRate, source.length, source.replacementCost, source.rating,
          source.lastUpdate, source.specialFeatures, source.fullText)}
  }

  @Serializable
  data class FilmActor(
    override val actorId: kotlin.Int,
    override val filmId: kotlin.Int,
    override val lastUpdate: kotlin.String
  ) : DvdRental.FilmActor {
    companion object {
      val path: String = "/DvdRental/FilmActor"

      fun create(source: DvdRental.FilmActor) = DvdRentalDto.FilmActor(source.actorId,
          source.filmId, source.lastUpdate)}
  }

  @Serializable
  data class FilmCategory(
    override val filmId: kotlin.Int,
    override val categoryId: kotlin.Int,
    override val lastUpdate: kotlin.String
  ) : DvdRental.FilmCategory {
    companion object {
      val path: String = "/DvdRental/FilmCategory"

      fun create(source: DvdRental.FilmCategory) = DvdRentalDto.FilmCategory(source.filmId,
          source.categoryId, source.lastUpdate)}
  }

  @Serializable
  data class Inventory(
    override val inventoryId: kotlin.Int,
    override val filmId: kotlin.Int,
    override val storeId: kotlin.Int,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Inventory {
    companion object {
      val path: String = "/DvdRental/Inventory"

      fun create(source: DvdRental.Inventory) = DvdRentalDto.Inventory(source.inventoryId,
          source.filmId, source.storeId, source.lastUpdate)}
  }

  @Serializable
  data class Language(
    override val languageId: kotlin.Int,
    override val name: kotlin.String,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Language {
    companion object {
      val path: String = "/DvdRental/Language"

      fun create(source: DvdRental.Language) = DvdRentalDto.Language(source.languageId, source.name,
          source.lastUpdate)}
  }

  @Serializable
  data class Payment(
    override val paymentId: kotlin.Int,
    override val customerId: kotlin.Int,
    override val staffId: kotlin.Int,
    override val rentalId: kotlin.Int,
    override val amount: kotlin.Double,
    override val paymentDate: kotlin.String
  ) : DvdRental.Payment {
    companion object {
      val path: String = "/DvdRental/Payment"

      fun create(source: DvdRental.Payment) = DvdRentalDto.Payment(source.paymentId,
          source.customerId, source.staffId, source.rentalId, source.amount, source.paymentDate)}
  }

  @Serializable
  data class Rental(
    override val rentalId: kotlin.Int,
    override val rentalDate: kotlin.String,
    override val inventoryId: kotlin.Int,
    override val customerId: kotlin.Int,
    override val returnDate: kotlin.String,
    override val staffId: kotlin.Int,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Rental {
    companion object {
      val path: String = "/DvdRental/Rental"

      fun create(source: DvdRental.Rental) = DvdRentalDto.Rental(source.rentalId, source.rentalDate,
          source.inventoryId, source.customerId, source.returnDate, source.staffId,
          source.lastUpdate)}
  }

  @Serializable
  data class Staff(
    override val staffId: kotlin.Int,
    override val firstName: kotlin.String,
    override val lastName: kotlin.String,
    override val addressId: kotlin.Int,
    override val email: kotlin.String,
    override val storeId: kotlin.Int,
    override val active: kotlin.String,
    override val username: kotlin.String,
    override val password: kotlin.String,
    override val lastUpdate: kotlin.String,
    override val picture: kotlin.String
  ) : DvdRental.Staff {
    companion object {
      val path: String = "/DvdRental/Staff"

      fun create(source: DvdRental.Staff) = DvdRentalDto.Staff(source.staffId, source.firstName,
          source.lastName, source.addressId, source.email, source.storeId, source.active,
          source.username, source.password, source.lastUpdate, source.picture)}
  }

  @Serializable
  data class Store(
    override val storeId: kotlin.Int,
    override val managerStaffId: kotlin.Int,
    override val addressId: kotlin.Int,
    override val lastUpdate: kotlin.String
  ) : DvdRental.Store {
    companion object {
      val path: String = "/DvdRental/Store"

      fun create(source: DvdRental.Store) = DvdRentalDto.Store(source.storeId,
          source.managerStaffId, source.addressId, source.lastUpdate)}
  }
}
