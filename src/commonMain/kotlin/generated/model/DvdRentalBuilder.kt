package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String

interface DvdRentalBuilder {
  class Actor(
    var actorId: Int?,
    var firstName: String?,
    var lastName: String?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Actor = DvdRentalDto.Actor(
    actorId ?: throw IllegalArgumentException("actorId is not nullable"),
    firstName ?: throw IllegalArgumentException("firstName is not nullable"),
    lastName ?: throw IllegalArgumentException("lastName is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Address(
    var addressId: Int?,
    var address: String?,
    var address2: String?,
    var district: String?,
    var cityId: Int?,
    var postalCode: String?,
    var phone: String?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Address = DvdRentalDto.Address(
    addressId ?: throw IllegalArgumentException("addressId is not nullable"),
    address ?: throw IllegalArgumentException("address is not nullable"),
    address2,
    district,
    cityId ?: throw IllegalArgumentException("cityId is not nullable"),
    postalCode,
    phone,
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Category(
    var categoryId: Int?,
    var name: String?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Category = DvdRentalDto.Category(
    categoryId ?: throw IllegalArgumentException("categoryId is not nullable"),
    name ?: throw IllegalArgumentException("name is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class City(
    var cityId: Int?,
    var city: String?,
    var countryId: Int?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.City = DvdRentalDto.City(
    cityId ?: throw IllegalArgumentException("cityId is not nullable"),
    city ?: throw IllegalArgumentException("city is not nullable"),
    countryId ?: throw IllegalArgumentException("countryId is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Country(
    var countryId: Int?,
    var country: String?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Country = DvdRentalDto.Country(
    countryId ?: throw IllegalArgumentException("countryId is not nullable"),
    country ?: throw IllegalArgumentException("country is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Customer(
    var customerId: Int?,
    var storeId: Int?,
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var addressId: Int?,
    var activebool: Boolean?,
    var createDate: String?,
    var lastUpdate: String?,
    var active: Int?
  ) {
    fun build(): DvdRental.Customer = DvdRentalDto.Customer(
    customerId ?: throw IllegalArgumentException("customerId is not nullable"),
    storeId ?: throw IllegalArgumentException("storeId is not nullable"),
    firstName ?: throw IllegalArgumentException("firstName is not nullable"),
    lastName ?: throw IllegalArgumentException("lastName is not nullable"),
    email ?: throw IllegalArgumentException("email is not nullable"),
    addressId ?: throw IllegalArgumentException("addressId is not nullable"),
    activebool ?: throw IllegalArgumentException("activebool is not nullable"),
    createDate ?: throw IllegalArgumentException("createDate is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable"),
    active ?: throw IllegalArgumentException("active is not nullable")
    )}

  class Film(
    var filmId: Int?,
    var title: String?,
    var description: String?,
    var releaseYear: Int?,
    var languageId: Int?,
    var rentalDuration: Int?,
    var rentalRate: Double?,
    var length: Int?,
    var replacementCost: Double?,
    var rating: String?,
    var lastUpdate: String?,
    var specialFeatures: String?,
    var fullText: String?
  ) {
    fun build(): DvdRental.Film = DvdRentalDto.Film(
    filmId ?: throw IllegalArgumentException("filmId is not nullable"),
    title ?: throw IllegalArgumentException("title is not nullable"),
    description ?: throw IllegalArgumentException("description is not nullable"),
    releaseYear ?: throw IllegalArgumentException("releaseYear is not nullable"),
    languageId ?: throw IllegalArgumentException("languageId is not nullable"),
    rentalDuration ?: throw IllegalArgumentException("rentalDuration is not nullable"),
    rentalRate ?: throw IllegalArgumentException("rentalRate is not nullable"),
    length ?: throw IllegalArgumentException("length is not nullable"),
    replacementCost ?: throw IllegalArgumentException("replacementCost is not nullable"),
    rating ?: throw IllegalArgumentException("rating is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable"),
    specialFeatures ?: throw IllegalArgumentException("specialFeatures is not nullable"),
    fullText ?: throw IllegalArgumentException("fullText is not nullable")
    )}

  class FilmActor(
    var actorId: Int?,
    var filmId: Int?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.FilmActor = DvdRentalDto.FilmActor(
    actorId ?: throw IllegalArgumentException("actorId is not nullable"),
    filmId ?: throw IllegalArgumentException("filmId is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class FilmCategory(
    var filmId: Int?,
    var categoryId: Int?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.FilmCategory = DvdRentalDto.FilmCategory(
    filmId ?: throw IllegalArgumentException("filmId is not nullable"),
    categoryId ?: throw IllegalArgumentException("categoryId is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Inventory(
    var inventoryId: Int?,
    var filmId: Int?,
    var storeId: Int?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Inventory = DvdRentalDto.Inventory(
    inventoryId ?: throw IllegalArgumentException("inventoryId is not nullable"),
    filmId ?: throw IllegalArgumentException("filmId is not nullable"),
    storeId ?: throw IllegalArgumentException("storeId is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Language(
    var languageId: Int?,
    var name: String?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Language = DvdRentalDto.Language(
    languageId ?: throw IllegalArgumentException("languageId is not nullable"),
    name ?: throw IllegalArgumentException("name is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Payment(
    var paymentId: Int?,
    var customerId: Int?,
    var staffId: Int?,
    var rentalId: Int?,
    var amount: Double?,
    var paymentDate: String?
  ) {
    fun build(): DvdRental.Payment = DvdRentalDto.Payment(
    paymentId ?: throw IllegalArgumentException("paymentId is not nullable"),
    customerId ?: throw IllegalArgumentException("customerId is not nullable"),
    staffId ?: throw IllegalArgumentException("staffId is not nullable"),
    rentalId ?: throw IllegalArgumentException("rentalId is not nullable"),
    amount ?: throw IllegalArgumentException("amount is not nullable"),
    paymentDate ?: throw IllegalArgumentException("paymentDate is not nullable")
    )}

  class Rental(
    var rentalId: Int?,
    var rentalDate: String?,
    var inventoryId: Int?,
    var customerId: Int?,
    var returnDate: String?,
    var staffId: Int?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Rental = DvdRentalDto.Rental(
    rentalId ?: throw IllegalArgumentException("rentalId is not nullable"),
    rentalDate ?: throw IllegalArgumentException("rentalDate is not nullable"),
    inventoryId ?: throw IllegalArgumentException("inventoryId is not nullable"),
    customerId ?: throw IllegalArgumentException("customerId is not nullable"),
    returnDate ?: throw IllegalArgumentException("returnDate is not nullable"),
    staffId ?: throw IllegalArgumentException("staffId is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}

  class Staff(
    var staffId: Int?,
    var firstName: String?,
    var lastName: String?,
    var addressId: Int?,
    var email: String?,
    var storeId: Int?,
    var active: String?,
    var username: String?,
    var password: String?,
    var lastUpdate: String?,
    var picture: String?
  ) {
    fun build(): DvdRental.Staff = DvdRentalDto.Staff(
    staffId ?: throw IllegalArgumentException("staffId is not nullable"),
    firstName ?: throw IllegalArgumentException("firstName is not nullable"),
    lastName ?: throw IllegalArgumentException("lastName is not nullable"),
    addressId ?: throw IllegalArgumentException("addressId is not nullable"),
    email ?: throw IllegalArgumentException("email is not nullable"),
    storeId ?: throw IllegalArgumentException("storeId is not nullable"),
    active ?: throw IllegalArgumentException("active is not nullable"),
    username ?: throw IllegalArgumentException("username is not nullable"),
    password ?: throw IllegalArgumentException("password is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable"),
    picture ?: throw IllegalArgumentException("picture is not nullable")
    )}

  class Store(
    var storeId: Int?,
    var managerStaffId: Int?,
    var addressId: Int?,
    var lastUpdate: String?
  ) {
    fun build(): DvdRental.Store = DvdRentalDto.Store(
    storeId ?: throw IllegalArgumentException("storeId is not nullable"),
    managerStaffId ?: throw IllegalArgumentException("managerStaffId is not nullable"),
    addressId ?: throw IllegalArgumentException("addressId is not nullable"),
    lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable")
    )}
}
