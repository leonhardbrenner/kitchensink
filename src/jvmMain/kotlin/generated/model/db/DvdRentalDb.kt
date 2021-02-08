package generated.model.db

import generated.model.DvdRental
import generated.model.DvdRentalDto
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DvdRentalDb {
  object Actor {
    fun create(source: ResultRow) = DvdRentalDto.Actor(source[Table.actorId],
        source[Table.firstName], source[Table.lastName], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Actor") {
      val actorId: Column<Int> = integer("actor_id")

      val firstName: Column<String> = text("first_name")

      val lastName: Column<String> = text("last_name")

      val lastUpdate: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Actor {
      override var actorId: Int by Table.actorId

      override var firstName: String by Table.firstName

      override var lastName: String by Table.lastName

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Actor) {
          Entity.new {
            actorId = source.actorId
            firstName = source.firstName
            lastName = source.lastName
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Address {
    fun create(source: ResultRow) = DvdRentalDto.Address(source[Table.address],
        source[Table.address2], source[Table.addressId], source[Table.cityId],
        source[Table.district], source[Table.lastUpdate], source[Table.phone],
        source[Table.postalCode])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Address") {
      val address: Column<String> = text("address")

      val address2: Column<String?> = text("address2").nullable()

      val addressId: Column<Int> = integer("address_id")

      val cityId: Column<Int> = integer("city_id")

      val district: Column<String?> = text("district").nullable()

      val lastUpdate: Column<String> = text("last_update")

      val phone: Column<String?> = text("phone").nullable()

      val postalCode: Column<String?> = text("postal_code").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Address {
      override var address: String by Table.address

      override var address2: String? by Table.address2

      override var addressId: Int by Table.addressId

      override var cityId: Int by Table.cityId

      override var district: String? by Table.district

      override var lastUpdate: String by Table.lastUpdate

      override var phone: String? by Table.phone

      override var postalCode: String? by Table.postalCode

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Address) {
          Entity.new {
            address = source.address
            address2 = source.address2
            addressId = source.addressId
            cityId = source.cityId
            district = source.district
            lastUpdate = source.lastUpdate
            phone = source.phone
            postalCode = source.postalCode
          }
        }
      }
    }
  }

  object Category {
    fun create(source: ResultRow) = DvdRentalDto.Category(source[Table.categoryId],
        source[Table.lastUpdate], source[Table.name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Category") {
      val categoryId: Column<Int> = integer("category_id")

      val lastUpdate: Column<String> = text("last_update")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Category {
      override var categoryId: Int by Table.categoryId

      override var lastUpdate: String by Table.lastUpdate

      override var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Category) {
          Entity.new {
            categoryId = source.categoryId
            lastUpdate = source.lastUpdate
            name = source.name
          }
        }
      }
    }
  }

  object City {
    fun create(source: ResultRow) = DvdRentalDto.City(source[Table.city], source[Table.cityId],
        source[Table.countryId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("City") {
      val city: Column<String> = text("city")

      val cityId: Column<Int> = integer("city_id")

      val countryId: Column<Int> = integer("country_id")

      val lastUpdate: Column<Int> = integer("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.City {
      override var city: String by Table.city

      override var cityId: Int by Table.cityId

      override var countryId: Int by Table.countryId

      override var lastUpdate: Int by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.City) {
          Entity.new {
            city = source.city
            cityId = source.cityId
            countryId = source.countryId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Country {
    fun create(source: ResultRow) = DvdRentalDto.Country(source[Table.country],
        source[Table.countryId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Country") {
      val country: Column<String> = text("country")

      val countryId: Column<Int> = integer("country_id")

      val lastUpdate: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Country {
      override var country: String by Table.country

      override var countryId: Int by Table.countryId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Country) {
          Entity.new {
            country = source.country
            countryId = source.countryId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Customer {
    fun create(source: ResultRow) = DvdRentalDto.Customer(source[Table.active],
        source[Table.activebool], source[Table.addressId], source[Table.createDate],
        source[Table.customerId], source[Table.email], source[Table.firstName],
        source[Table.lastName], source[Table.lastUpdate], source[Table.storeId])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Customer") {
      val active: Column<Int> = integer("active")

      val activebool: Column<Boolean> = bool("activebool")

      val addressId: Column<Int> = integer("address_id")

      val createDate: Column<String> = text("create_date")

      val customerId: Column<Int> = integer("customer_id")

      val email: Column<String> = text("email")

      val firstName: Column<String> = text("first_name")

      val lastName: Column<String> = text("last_name")

      val lastUpdate: Column<String> = text("last_update")

      val storeId: Column<Int> = integer("store_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Customer {
      override var active: Int by Table.active

      override var activebool: Boolean by Table.activebool

      override var addressId: Int by Table.addressId

      override var createDate: String by Table.createDate

      override var customerId: Int by Table.customerId

      override var email: String by Table.email

      override var firstName: String by Table.firstName

      override var lastName: String by Table.lastName

      override var lastUpdate: String by Table.lastUpdate

      override var storeId: Int by Table.storeId

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Customer) {
          Entity.new {
            active = source.active
            activebool = source.activebool
            addressId = source.addressId
            createDate = source.createDate
            customerId = source.customerId
            email = source.email
            firstName = source.firstName
            lastName = source.lastName
            lastUpdate = source.lastUpdate
            storeId = source.storeId
          }
        }
      }
    }
  }

  object Film {
    fun create(source: ResultRow) = DvdRentalDto.Film(source[Table.description],
        source[Table.filmId], source[Table.fullText], source[Table.languageId],
        source[Table.lastUpdate], source[Table.length], source[Table.rating],
        source[Table.releaseYear], source[Table.rentalDuration], source[Table.rentalRate],
        source[Table.replacementCost], source[Table.specialFeatures], source[Table.title])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Film") {
      val description: Column<String> = text("description")

      val filmId: Column<Int> = integer("film_id")

      val fullText: Column<String> = text("fulltext")

      val languageId: Column<Int> = integer("language_id")

      val lastUpdate: Column<String> = text("last_update")

      val length: Column<Int> = integer("length")

      val rating: Column<String> = text("rating")

      val releaseYear: Column<Int> = integer("release_year")

      val rentalDuration: Column<Int> = integer("rental_duration")

      val rentalRate: Column<Double> = double("rental_rate")

      val replacementCost: Column<Double> = double("replacement_cost")

      val specialFeatures: Column<String> = text("special_features")

      val title: Column<String> = text("title")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Film {
      override var description: String by Table.description

      override var filmId: Int by Table.filmId

      override var fullText: String by Table.fullText

      override var languageId: Int by Table.languageId

      override var lastUpdate: String by Table.lastUpdate

      override var length: Int by Table.length

      override var rating: String by Table.rating

      override var releaseYear: Int by Table.releaseYear

      override var rentalDuration: Int by Table.rentalDuration

      override var rentalRate: Double by Table.rentalRate

      override var replacementCost: Double by Table.replacementCost

      override var specialFeatures: String by Table.specialFeatures

      override var title: String by Table.title

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Film) {
          Entity.new {
            description = source.description
            filmId = source.filmId
            fullText = source.fullText
            languageId = source.languageId
            lastUpdate = source.lastUpdate
            length = source.length
            rating = source.rating
            releaseYear = source.releaseYear
            rentalDuration = source.rentalDuration
            rentalRate = source.rentalRate
            replacementCost = source.replacementCost
            specialFeatures = source.specialFeatures
            title = source.title
          }
        }
      }
    }
  }

  object FilmActor {
    fun create(source: ResultRow) = DvdRentalDto.FilmActor(source[Table.actorId],
        source[Table.filmId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("FilmActor") {
      val actorId: Column<Int> = integer("actor_id")

      val filmId: Column<Int> = integer("film_id")

      val lastUpdate: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.FilmActor {
      override var actorId: Int by Table.actorId

      override var filmId: Int by Table.filmId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.FilmActor) {
          Entity.new {
            actorId = source.actorId
            filmId = source.filmId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object FilmCategory {
    fun create(source: ResultRow) = DvdRentalDto.FilmCategory(source[Table.categoryId],
        source[Table.filmId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("FilmCategory") {
      val categoryId: Column<Int> = integer("category_id")

      val filmId: Column<Int> = integer("film_id")

      val lastUpdate: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.FilmCategory {
      override var categoryId: Int by Table.categoryId

      override var filmId: Int by Table.filmId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.FilmCategory) {
          Entity.new {
            categoryId = source.categoryId
            filmId = source.filmId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Inventory {
    fun create(source: ResultRow) = DvdRentalDto.Inventory(source[Table.filmId],
        source[Table.inventoryId], source[Table.lastUpdate], source[Table.storeId])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Inventory") {
      val filmId: Column<Int> = integer("film_id")

      val inventoryId: Column<Int> = integer("inventory_id")

      val lastUpdate: Column<String> = text("last_update")

      val storeId: Column<Int> = integer("store_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Inventory {
      override var filmId: Int by Table.filmId

      override var inventoryId: Int by Table.inventoryId

      override var lastUpdate: String by Table.lastUpdate

      override var storeId: Int by Table.storeId

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Inventory) {
          Entity.new {
            filmId = source.filmId
            inventoryId = source.inventoryId
            lastUpdate = source.lastUpdate
            storeId = source.storeId
          }
        }
      }
    }
  }

  object Language {
    fun create(source: ResultRow) = DvdRentalDto.Language(source[Table.languageId],
        source[Table.lastUpdate], source[Table.name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Language") {
      val languageId: Column<Int> = integer("language_id")

      val lastUpdate: Column<String> = text("last_update")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Language {
      override var languageId: Int by Table.languageId

      override var lastUpdate: String by Table.lastUpdate

      override var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Language) {
          Entity.new {
            languageId = source.languageId
            lastUpdate = source.lastUpdate
            name = source.name
          }
        }
      }
    }
  }

  object Payment {
    fun create(source: ResultRow) = DvdRentalDto.Payment(source[Table.amount],
        source[Table.customerId], source[Table.paymentDate], source[Table.paymentId],
        source[Table.rentalId], source[Table.staffId])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Payment") {
      val amount: Column<Double> = double("amount")

      val customerId: Column<Int> = integer("customer_id")

      val paymentDate: Column<String> = text("payment_date")

      val paymentId: Column<Int> = integer("payment_id")

      val rentalId: Column<Int> = integer("rental_id")

      val staffId: Column<Int> = integer("staff_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Payment {
      override var amount: Double by Table.amount

      override var customerId: Int by Table.customerId

      override var paymentDate: String by Table.paymentDate

      override var paymentId: Int by Table.paymentId

      override var rentalId: Int by Table.rentalId

      override var staffId: Int by Table.staffId

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Payment) {
          Entity.new {
            amount = source.amount
            customerId = source.customerId
            paymentDate = source.paymentDate
            paymentId = source.paymentId
            rentalId = source.rentalId
            staffId = source.staffId
          }
        }
      }
    }
  }

  object Rental {
    fun create(source: ResultRow) = DvdRentalDto.Rental(source[Table.customerId],
        source[Table.inventoryId], source[Table.lastUpdate], source[Table.rentalDate],
        source[Table.rentalId], source[Table.returnDate], source[Table.staffId])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Rental") {
      val customerId: Column<Int> = integer("customer_id")

      val inventoryId: Column<Int> = integer("inventory_id")

      val lastUpdate: Column<String> = text("last_update")

      val rentalDate: Column<Int> = integer("rental_date")

      val rentalId: Column<Int> = integer("rental_id")

      val returnDate: Column<String> = text("return_date")

      val staffId: Column<Int> = integer("staff_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Rental {
      override var customerId: Int by Table.customerId

      override var inventoryId: Int by Table.inventoryId

      override var lastUpdate: String by Table.lastUpdate

      override var rentalDate: Int by Table.rentalDate

      override var rentalId: Int by Table.rentalId

      override var returnDate: String by Table.returnDate

      override var staffId: Int by Table.staffId

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Rental) {
          Entity.new {
            customerId = source.customerId
            inventoryId = source.inventoryId
            lastUpdate = source.lastUpdate
            rentalDate = source.rentalDate
            rentalId = source.rentalId
            returnDate = source.returnDate
            staffId = source.staffId
          }
        }
      }
    }
  }

  object Staff {
    fun create(source: ResultRow) = DvdRentalDto.Staff(source[Table.active],
        source[Table.addressId], source[Table.email], source[Table.firstName],
        source[Table.lastName], source[Table.lastUpdate], source[Table.password],
        source[Table.picture], source[Table.staffId], source[Table.storeId], source[Table.username])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Staff") {
      val active: Column<Int> = integer("active")

      val addressId: Column<Int> = integer("address_id")

      val email: Column<Int> = integer("email")

      val firstName: Column<String> = text("first_name")

      val lastName: Column<String> = text("last_name")

      val lastUpdate: Column<String> = text("last_update")

      val password: Column<String> = text("password")

      val picture: Column<Int> = integer("picture")

      val staffId: Column<Int> = integer("staff_id")

      val storeId: Column<Int> = integer("store_id")

      val username: Column<String> = text("username")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Staff {
      override var active: Int by Table.active

      override var addressId: Int by Table.addressId

      override var email: Int by Table.email

      override var firstName: String by Table.firstName

      override var lastName: String by Table.lastName

      override var lastUpdate: String by Table.lastUpdate

      override var password: String by Table.password

      override var picture: Int by Table.picture

      override var staffId: Int by Table.staffId

      override var storeId: Int by Table.storeId

      override var username: String by Table.username

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Staff) {
          Entity.new {
            active = source.active
            addressId = source.addressId
            email = source.email
            firstName = source.firstName
            lastName = source.lastName
            lastUpdate = source.lastUpdate
            password = source.password
            picture = source.picture
            staffId = source.staffId
            storeId = source.storeId
            username = source.username
          }
        }
      }
    }
  }

  object Store {
    fun create(source: ResultRow) = DvdRentalDto.Store(source[Table.addressId],
        source[Table.lastUpdate], source[Table.managerStaffId], source[Table.storeId])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Store") {
      val addressId: Column<Int> = integer("address_id")

      val lastUpdate: Column<String> = text("last_update")

      val managerStaffId: Column<Int> = integer("manager_staff_id")

      val storeId: Column<Int> = integer("store_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Store {
      override var addressId: Int by Table.addressId

      override var lastUpdate: String by Table.lastUpdate

      override var managerStaffId: Int by Table.managerStaffId

      override var storeId: Int by Table.storeId

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.Store) {
          Entity.new {
            addressId = source.addressId
            lastUpdate = source.lastUpdate
            managerStaffId = source.managerStaffId
            storeId = source.storeId
          }
        }
      }
    }
  }
}
