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
      val actorId: Column<Int> = integer("actorId")

      val firstName: Column<String> = text("firstName")

      val lastName: Column<String> = text("lastName")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Actor {
      override var actorId: Int by Table.actorId

      override var firstName: String by Table.firstName

      override var lastName: String by Table.lastName

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Actor) {
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
    fun create(source: ResultRow) = DvdRentalDto.Address(source[Table.addressId],
        source[Table.address], source[Table.address2], source[Table.district], source[Table.cityId],
        source[Table.postalCode], source[Table.phone], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Address") {
      val addressId: Column<Int> = integer("addressId")

      val address: Column<String> = text("address")

      val address2: Column<String?> = text("address2").nullable()

      val district: Column<String?> = text("district").nullable()

      val cityId: Column<Int> = integer("cityId")

      val postalCode: Column<String?> = text("postalCode").nullable()

      val phone: Column<String?> = text("phone").nullable()

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Address {
      override var addressId: Int by Table.addressId

      override var address: String by Table.address

      override var address2: String? by Table.address2

      override var district: String? by Table.district

      override var cityId: Int by Table.cityId

      override var postalCode: String? by Table.postalCode

      override var phone: String? by Table.phone

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Address) {
          Entity.new {
            addressId = source.addressId
            address = source.address
            address2 = source.address2
            district = source.district
            cityId = source.cityId
            postalCode = source.postalCode
            phone = source.phone
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Category {
    fun create(source: ResultRow) = DvdRentalDto.Category(source[Table.categoryId],
        source[Table.name], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Category") {
      val categoryId: Column<Int> = integer("categoryId")

      val name: Column<String> = text("name")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Category {
      override var categoryId: Int by Table.categoryId

      override var name: String by Table.name

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Category) {
          Entity.new {
            categoryId = source.categoryId
            name = source.name
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object City {
    fun create(source: ResultRow) = DvdRentalDto.City(source[Table.cityId], source[Table.city],
        source[Table.countryId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("City") {
      val cityId: Column<Int> = integer("cityId")

      val city: Column<String> = text("city")

      val countryId: Column<Int> = integer("countryId")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.City {
      override var cityId: Int by Table.cityId

      override var city: String by Table.city

      override var countryId: Int by Table.countryId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.City) {
          Entity.new {
            cityId = source.cityId
            city = source.city
            countryId = source.countryId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Country {
    fun create(source: ResultRow) = DvdRentalDto.Country(source[Table.countryId],
        source[Table.country], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Country") {
      val countryId: Column<Int> = integer("countryId")

      val country: Column<String> = text("country")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Country {
      override var countryId: Int by Table.countryId

      override var country: String by Table.country

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Country) {
          Entity.new {
            countryId = source.countryId
            country = source.country
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Customer {
    fun create(source: ResultRow) = DvdRentalDto.Customer(source[Table.customerId],
        source[Table.storeId], source[Table.firstName], source[Table.lastName], source[Table.email],
        source[Table.addressId], source[Table.activebool], source[Table.createDate],
        source[Table.lastUpdate], source[Table.active])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Customer") {
      val customerId: Column<Int> = integer("customerId")

      val storeId: Column<Int> = integer("storeId")

      val firstName: Column<String> = text("firstName")

      val lastName: Column<String> = text("lastName")

      val email: Column<String> = text("email")

      val addressId: Column<Int> = integer("addressId")

      val activebool: Column<Boolean> = bool("activebool")

      val createDate: Column<String> = text("createDate")

      val lastUpdate: Column<String> = text("lastUpdate")

      val active: Column<Int> = integer("active")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Customer {
      override var customerId: Int by Table.customerId

      override var storeId: Int by Table.storeId

      override var firstName: String by Table.firstName

      override var lastName: String by Table.lastName

      override var email: String by Table.email

      override var addressId: Int by Table.addressId

      override var activebool: Boolean by Table.activebool

      override var createDate: String by Table.createDate

      override var lastUpdate: String by Table.lastUpdate

      override var active: Int by Table.active

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Customer) {
          Entity.new {
            customerId = source.customerId
            storeId = source.storeId
            firstName = source.firstName
            lastName = source.lastName
            email = source.email
            addressId = source.addressId
            activebool = source.activebool
            createDate = source.createDate
            lastUpdate = source.lastUpdate
            active = source.active
          }
        }
      }
    }
  }

  object Film {
    fun create(source: ResultRow) = DvdRentalDto.Film(source[Table.filmId], source[Table.title],
        source[Table.description], source[Table.releaseYear], source[Table.languageId],
        source[Table.rentalDuration], source[Table.rentalRate], source[Table.length],
        source[Table.replacementCost], source[Table.rating], source[Table.lastUpdate],
        source[Table.specialFeatures], source[Table.fullText])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Film") {
      val filmId: Column<Int> = integer("filmId")

      val title: Column<String> = text("title")

      val description: Column<String> = text("description")

      val releaseYear: Column<Int> = integer("releaseYear")

      val languageId: Column<Int> = integer("languageId")

      val rentalDuration: Column<Int> = integer("rentalDuration")

      val rentalRate: Column<Double> = double("rentalRate")

      val length: Column<Int> = integer("length")

      val replacementCost: Column<Double> = double("replacementCost")

      val rating: Column<String> = text("rating")

      val lastUpdate: Column<String> = text("lastUpdate")

      val specialFeatures: Column<String> = text("specialFeatures")

      val fullText: Column<String> = text("fullText")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Film {
      override var filmId: Int by Table.filmId

      override var title: String by Table.title

      override var description: String by Table.description

      override var releaseYear: Int by Table.releaseYear

      override var languageId: Int by Table.languageId

      override var rentalDuration: Int by Table.rentalDuration

      override var rentalRate: Double by Table.rentalRate

      override var length: Int by Table.length

      override var replacementCost: Double by Table.replacementCost

      override var rating: String by Table.rating

      override var lastUpdate: String by Table.lastUpdate

      override var specialFeatures: String by Table.specialFeatures

      override var fullText: String by Table.fullText

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Film) {
          Entity.new {
            filmId = source.filmId
            title = source.title
            description = source.description
            releaseYear = source.releaseYear
            languageId = source.languageId
            rentalDuration = source.rentalDuration
            rentalRate = source.rentalRate
            length = source.length
            replacementCost = source.replacementCost
            rating = source.rating
            lastUpdate = source.lastUpdate
            specialFeatures = source.specialFeatures
            fullText = source.fullText
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
      val actorId: Column<Int> = integer("actorId")

      val filmId: Column<Int> = integer("filmId")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.FilmActor {
      override var actorId: Int by Table.actorId

      override var filmId: Int by Table.filmId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.FilmActor) {
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
    fun create(source: ResultRow) = DvdRentalDto.FilmCategory(source[Table.filmId],
        source[Table.categoryId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("FilmCategory") {
      val filmId: Column<Int> = integer("filmId")

      val categoryId: Column<Int> = integer("categoryId")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.FilmCategory {
      override var filmId: Int by Table.filmId

      override var categoryId: Int by Table.categoryId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.FilmCategory) {
          Entity.new {
            filmId = source.filmId
            categoryId = source.categoryId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Inventory {
    fun create(source: ResultRow) = DvdRentalDto.Inventory(source[Table.inventoryId],
        source[Table.filmId], source[Table.storeId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Inventory") {
      val inventoryId: Column<Int> = integer("inventoryId")

      val filmId: Column<Int> = integer("filmId")

      val storeId: Column<Int> = integer("storeId")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Inventory {
      override var inventoryId: Int by Table.inventoryId

      override var filmId: Int by Table.filmId

      override var storeId: Int by Table.storeId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Inventory) {
          Entity.new {
            inventoryId = source.inventoryId
            filmId = source.filmId
            storeId = source.storeId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Language {
    fun create(source: ResultRow) = DvdRentalDto.Language(source[Table.languageId],
        source[Table.name], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Language") {
      val languageId: Column<Int> = integer("languageId")

      val name: Column<String> = text("name")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Language {
      override var languageId: Int by Table.languageId

      override var name: String by Table.name

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Language) {
          Entity.new {
            languageId = source.languageId
            name = source.name
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Payment {
    fun create(source: ResultRow) = DvdRentalDto.Payment(source[Table.paymentId],
        source[Table.customerId], source[Table.staffId], source[Table.rentalId],
        source[Table.amount], source[Table.paymentDate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Payment") {
      val paymentId: Column<Int> = integer("paymentId")

      val customerId: Column<Int> = integer("customerId")

      val staffId: Column<Int> = integer("staffId")

      val rentalId: Column<Int> = integer("rentalId")

      val amount: Column<Double> = double("amount")

      val paymentDate: Column<String> = text("paymentDate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Payment {
      override var paymentId: Int by Table.paymentId

      override var customerId: Int by Table.customerId

      override var staffId: Int by Table.staffId

      override var rentalId: Int by Table.rentalId

      override var amount: Double by Table.amount

      override var paymentDate: String by Table.paymentDate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Payment) {
          Entity.new {
            paymentId = source.paymentId
            customerId = source.customerId
            staffId = source.staffId
            rentalId = source.rentalId
            amount = source.amount
            paymentDate = source.paymentDate
          }
        }
      }
    }
  }

  object Rental {
    fun create(source: ResultRow) = DvdRentalDto.Rental(source[Table.rentalId],
        source[Table.rentalDate], source[Table.inventoryId], source[Table.customerId],
        source[Table.returnDate], source[Table.staffId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Rental") {
      val rentalId: Column<Int> = integer("rentalId")

      val rentalDate: Column<String> = text("rentalDate")

      val inventoryId: Column<Int> = integer("inventoryId")

      val customerId: Column<Int> = integer("customerId")

      val returnDate: Column<String> = text("returnDate")

      val staffId: Column<Int> = integer("staffId")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Rental {
      override var rentalId: Int by Table.rentalId

      override var rentalDate: String by Table.rentalDate

      override var inventoryId: Int by Table.inventoryId

      override var customerId: Int by Table.customerId

      override var returnDate: String by Table.returnDate

      override var staffId: Int by Table.staffId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Rental) {
          Entity.new {
            rentalId = source.rentalId
            rentalDate = source.rentalDate
            inventoryId = source.inventoryId
            customerId = source.customerId
            returnDate = source.returnDate
            staffId = source.staffId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }

  object Staff {
    fun create(source: ResultRow) = DvdRentalDto.Staff(source[Table.staffId],
        source[Table.firstName], source[Table.lastName], source[Table.addressId],
        source[Table.email], source[Table.storeId], source[Table.active], source[Table.username],
        source[Table.password], source[Table.lastUpdate], source[Table.picture])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Staff") {
      val staffId: Column<Int> = integer("staffId")

      val firstName: Column<String> = text("firstName")

      val lastName: Column<String> = text("lastName")

      val addressId: Column<Int> = integer("addressId")

      val email: Column<String> = text("email")

      val storeId: Column<Int> = integer("storeId")

      val active: Column<String> = text("active")

      val username: Column<String> = text("username")

      val password: Column<String> = text("password")

      val lastUpdate: Column<String> = text("lastUpdate")

      val picture: Column<String> = text("picture")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Staff {
      override var staffId: Int by Table.staffId

      override var firstName: String by Table.firstName

      override var lastName: String by Table.lastName

      override var addressId: Int by Table.addressId

      override var email: String by Table.email

      override var storeId: Int by Table.storeId

      override var active: String by Table.active

      override var username: String by Table.username

      override var password: String by Table.password

      override var lastUpdate: String by Table.lastUpdate

      override var picture: String by Table.picture

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Staff) {
          Entity.new {
            staffId = source.staffId
            firstName = source.firstName
            lastName = source.lastName
            addressId = source.addressId
            email = source.email
            storeId = source.storeId
            active = source.active
            username = source.username
            password = source.password
            lastUpdate = source.lastUpdate
            picture = source.picture
          }
        }
      }
    }
  }

  object Store {
    fun create(source: ResultRow) = DvdRentalDto.Store(source[Table.storeId],
        source[Table.managerStaffId], source[Table.addressId], source[Table.lastUpdate])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Store") {
      val storeId: Column<Int> = integer("storeId")

      val managerStaffId: Column<Int> = integer("managerStaffId")

      val addressId: Column<Int> = integer("addressId")

      val lastUpdate: Column<String> = text("lastUpdate")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.Store {
      override var storeId: Int by Table.storeId

      override var managerStaffId: Int by Table.managerStaffId

      override var addressId: Int by Table.addressId

      override var lastUpdate: String by Table.lastUpdate

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: DvdRental.Store) {
          Entity.new {
            storeId = source.storeId
            managerStaffId = source.managerStaffId
            addressId = source.addressId
            lastUpdate = source.lastUpdate
          }
        }
      }
    }
  }
}
