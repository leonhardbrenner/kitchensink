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
      val header: String =
          "addressId\taddress\taddress2\tdistrict\tcityId\tpostalCode\tphone\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Address>(file, header)}
  }

  data class Category(
    override val categoryId: Int,
    override val name: String,
    override val lastUpdate: String
  ) : DvdRental.Category {
    companion object {
      val header: String = "categoryId\tname\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Category>(file, header)}
  }

  data class City(
    override val cityId: Int,
    override val city: String,
    override val countryId: Int,
    override val lastUpdate: String
  ) : DvdRental.City {
    companion object {
      val header: String = "cityId\tcity\tcountryId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<City>(file, header)}
  }

  data class Country(
    override val countryId: Int,
    override val country: String,
    override val lastUpdate: String
  ) : DvdRental.Country {
    companion object {
      val header: String = "countryId\tcountry\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Country>(file, header)}
  }

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
      val header: String =
          "customerId\tstoreId\tfirstName\tlastName\temail\taddressId\tactivebool\tcreateDate\tlastUpdate\tactive"

      fun loadCsv(file: File) = model.loadCsv<Customer>(file, header)}
  }

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
      val header: String =
          "filmId\ttitle\tdescription\treleaseYear\tlanguageId\trentalDuration\trentalRate\tlength\treplacementCost\trating\tlastUpdate\tspecialFeatures\tfullText"

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
    override val filmId: Int,
    override val categoryId: Int,
    override val lastUpdate: String
  ) : DvdRental.FilmCategory {
    companion object {
      val header: String = "filmId\tcategoryId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<FilmCategory>(file, header)}
  }

  data class Inventory(
    override val inventoryId: Int,
    override val filmId: Int,
    override val storeId: Int,
    override val lastUpdate: String
  ) : DvdRental.Inventory {
    companion object {
      val header: String = "inventoryId\tfilmId\tstoreId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Inventory>(file, header)}
  }

  data class Language(
    override val languageId: Int,
    override val name: String,
    override val lastUpdate: String
  ) : DvdRental.Language {
    companion object {
      val header: String = "languageId\tname\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Language>(file, header)}
  }

  data class Payment(
    override val paymentId: Int,
    override val customerId: Int,
    override val staffId: Int,
    override val rentalId: Int,
    override val amount: Double,
    override val paymentDate: String
  ) : DvdRental.Payment {
    companion object {
      val header: String = "paymentId\tcustomerId\tstaffId\trentalId\tamount\tpaymentDate"

      fun loadCsv(file: File) = model.loadCsv<Payment>(file, header)}
  }

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
      val header: String =
          "rentalId\trentalDate\tinventoryId\tcustomerId\treturnDate\tstaffId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Rental>(file, header)}
  }

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
      val header: String =
          "staffId\tfirstName\tlastName\taddressId\temail\tstoreId\tactive\tusername\tpassword\tlastUpdate\tpicture"

      fun loadCsv(file: File) = model.loadCsv<Staff>(file, header)}
  }

  data class Store(
    override val storeId: Int,
    override val managerStaffId: Int,
    override val addressId: Int,
    override val lastUpdate: String
  ) : DvdRental.Store {
    companion object {
      val header: String = "storeId\tmanagerStaffId\taddressId\tlastUpdate"

      fun loadCsv(file: File) = model.loadCsv<Store>(file, header)}
  }
}
