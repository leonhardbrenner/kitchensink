package generated.model

import java.io.File
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String

interface DvdRentalCsvLoader {
  data class Actor(
    override val actorId: Int,
    override val firstName: String,
    override val lastName: String,
    override val lastUpdate: String
  ) : DvdRental.Actor {
    companion object {
      val header: String = "actorId\tfirstName\tlastName\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Actor>(file, header)}
  }

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
      val header: String =
          "address\taddress2\taddressId\tcityId\tdistrict\tlastUpdate\tphone\tpostalCode"

      fun loadCsv(file: File) = model.loadCsv<Address>(file, header)}
  }

  data class Category(
    override val categoryId: Int,
    override val lastUpdate: String,
    override val name: String
  ) : DvdRental.Category {
    companion object {
      val header: String = "categoryId\tlastUpdate\tname"

      fun loadCsv(file: File) = model.loadCsv<Category>(file, header)}
  }

  data class City(
    override val city: String,
    override val cityId: Int,
    override val countryId: Int,
    override val lastUpdate: Int
  ) : DvdRental.City {
    companion object {
      val header: String = "city\tcityId\tcountryId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<City>(file, header)}
  }

  data class Country(
    override val country: String,
    override val countryId: Int,
    override val lastUpdate: String
  ) : DvdRental.Country {
    companion object {
      val header: String = "country\tcountryId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Country>(file, header)}
  }

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
      val header: String =
          "active\tactivebool\taddressId\tcreateDate\tcustomerId\temail\tfirstName\tlastName\tlastUpdate\tstoreId"

      fun loadCsv(file: File) = model.loadCsv<Customer>(file, header)}
  }

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
      val header: String =
          "description\tfilmId\tfullText\tlanguageId\tlastUpdate\tlength\trating\treleaseYear\trentalDuration\trentalRate\treplacementCost\tspecialFeatures\ttitle"

      fun loadCsv(file: File) = model.loadCsv<Film>(file, header)}
  }

  data class FilmActor(
    override val actorId: Int,
    override val filmId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmActor {
    companion object {
      val header: String = "actorId\tfilmId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<FilmActor>(file, header)}
  }

  data class FilmCategory(
    override val categoryId: Int,
    override val filmId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmCategory {
    companion object {
      val header: String = "categoryId\tfilmId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<FilmCategory>(file, header)}
  }

  data class Inventory(
    override val filmId: Int,
    override val inventoryId: Int,
    override val lastUpdate: String,
    override val storeId: Int
  ) : DvdRental.Inventory {
    companion object {
      val header: String = "filmId\tinventoryId\tlastUpdate\tstoreId"

      fun loadCsv(file: File) = model.loadCsv<Inventory>(file, header)}
  }

  data class Language(
    override val languageId: Int,
    override val lastUpdate: String,
    override val name: String
  ) : DvdRental.Language {
    companion object {
      val header: String = "languageId\tlastUpdate\tname"

      fun loadCsv(file: File) = model.loadCsv<Language>(file, header)}
  }

  data class Payment(
    override val amount: Double,
    override val customerId: Int,
    override val paymentDate: String,
    override val paymentId: Int,
    override val rentalId: Int,
    override val staffId: Int
  ) : DvdRental.Payment {
    companion object {
      val header: String = "amount\tcustomerId\tpaymentDate\tpaymentId\trentalId\tstaffId"

      fun loadCsv(file: File) = model.loadCsv<Payment>(file, header)}
  }

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
      val header: String =
          "customerId\tinventoryId\tlastUpdate\trentalDate\trentalId\treturnDate\tstaffId"

      fun loadCsv(file: File) = model.loadCsv<Rental>(file, header)}
  }

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
      val header: String =
          "active\taddressId\temail\tfirstName\tlastName\tlastUpdate\tpassword\tpicture\tstaffId\tstoreId\tusername"

      fun loadCsv(file: File) = model.loadCsv<Staff>(file, header)}
  }

  data class Store(
    override val addressId: Int,
    override val lastUpdate: String,
    override val managerStaffId: Int,
    override val storeId: Int
  ) : DvdRental.Store {
    companion object {
      val header: String = "addressId\tlastUpdate\tmanagerStaffId\tstoreId"

      fun loadCsv(file: File) = model.loadCsv<Store>(file, header)}
  }
}
