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
  object actor {
    fun create(source: ResultRow) = DvdRentalDto.actor(source[Table.actor_id],
        source[Table.first_name], source[Table.last_name], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("actor") {
      val actor_id: Column<Int> = integer("actor_id")

      val first_name: Column<String> = text("first_name")

      val last_name: Column<String> = text("last_name")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.actor {
      override var actor_id: Int by Table.actor_id

      override var first_name: String by Table.first_name

      override var last_name: String by Table.last_name

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.actor) {
          Entity.new {
            actor_id = source.actor_id
            first_name = source.first_name
            last_name = source.last_name
            last_update = source.last_update
          }
        }
      }
    }
  }

  object address {
    fun create(source: ResultRow) = DvdRentalDto.address(source[Table.address_id],
        source[Table.address], source[Table.address2], source[Table.district],
        source[Table.city_id], source[Table.postal_code], source[Table.phone],
        source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("address") {
      val address_id: Column<Int> = integer("address_id")

      val address: Column<String> = text("address")

      val address2: Column<String?> = text("address2").nullable()

      val district: Column<String?> = text("district").nullable()

      val city_id: Column<Int> = integer("city_id")

      val postal_code: Column<String?> = text("postal_code").nullable()

      val phone: Column<String?> = text("phone").nullable()

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.address {
      override var address_id: Int by Table.address_id

      override var address: String by Table.address

      override var address2: String? by Table.address2

      override var district: String? by Table.district

      override var city_id: Int by Table.city_id

      override var postal_code: String? by Table.postal_code

      override var phone: String? by Table.phone

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.address) {
          Entity.new {
            address_id = source.address_id
            address = source.address
            address2 = source.address2
            district = source.district
            city_id = source.city_id
            postal_code = source.postal_code
            phone = source.phone
            last_update = source.last_update
          }
        }
      }
    }
  }

  object category {
    fun create(source: ResultRow) = DvdRentalDto.category(source[Table.category_id],
        source[Table.name], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("category") {
      val category_id: Column<Int> = integer("category_id")

      val name: Column<String> = text("name")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.category {
      override var category_id: Int by Table.category_id

      override var name: String by Table.name

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.category) {
          Entity.new {
            category_id = source.category_id
            name = source.name
            last_update = source.last_update
          }
        }
      }
    }
  }

  object city {
    fun create(source: ResultRow) = DvdRentalDto.city(source[Table.city_id], source[Table.city],
        source[Table.country_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("city") {
      val city_id: Column<Int> = integer("city_id")

      val city: Column<String> = text("city")

      val country_id: Column<Int> = integer("country_id")

      val last_update: Column<Int> = integer("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.city {
      override var city_id: Int by Table.city_id

      override var city: String by Table.city

      override var country_id: Int by Table.country_id

      override var last_update: Int by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.city) {
          Entity.new {
            city_id = source.city_id
            city = source.city
            country_id = source.country_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object country {
    fun create(source: ResultRow) = DvdRentalDto.country(source[Table.country_id],
        source[Table.country], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("country") {
      val country_id: Column<Int> = integer("country_id")

      val country: Column<String> = text("country")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.country {
      override var country_id: Int by Table.country_id

      override var country: String by Table.country

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.country) {
          Entity.new {
            country_id = source.country_id
            country = source.country
            last_update = source.last_update
          }
        }
      }
    }
  }

  object customer {
    fun create(source: ResultRow) = DvdRentalDto.customer(source[Table.customer_id],
        source[Table.store_id], source[Table.first_name], source[Table.last_name],
        source[Table.email], source[Table.address_id], source[Table.activebool],
        source[Table.create_date], source[Table.last_update], source[Table.active])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("customer") {
      val customer_id: Column<Int> = integer("customer_id")

      val store_id: Column<Int> = integer("store_id")

      val first_name: Column<String> = text("first_name")

      val last_name: Column<String> = text("last_name")

      val email: Column<String> = text("email")

      val address_id: Column<Int> = integer("address_id")

      val activebool: Column<Boolean> = bool("activebool")

      val create_date: Column<String> = text("create_date")

      val last_update: Column<String> = text("last_update")

      val active: Column<Int> = integer("active")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.customer {
      override var customer_id: Int by Table.customer_id

      override var store_id: Int by Table.store_id

      override var first_name: String by Table.first_name

      override var last_name: String by Table.last_name

      override var email: String by Table.email

      override var address_id: Int by Table.address_id

      override var activebool: Boolean by Table.activebool

      override var create_date: String by Table.create_date

      override var last_update: String by Table.last_update

      override var active: Int by Table.active

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.customer) {
          Entity.new {
            customer_id = source.customer_id
            store_id = source.store_id
            first_name = source.first_name
            last_name = source.last_name
            email = source.email
            address_id = source.address_id
            activebool = source.activebool
            create_date = source.create_date
            last_update = source.last_update
            active = source.active
          }
        }
      }
    }
  }

  object film {
    fun create(source: ResultRow) = DvdRentalDto.film(source[Table.film_id], source[Table.title],
        source[Table.description], source[Table.release_year], source[Table.language_id],
        source[Table.rental_duration], source[Table.rental_rate], source[Table.length],
        source[Table.replacement_cost], source[Table.rating], source[Table.last_update],
        source[Table.special_features], source[Table.fulltext])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("film") {
      val film_id: Column<Int> = integer("film_id")

      val title: Column<String> = text("title")

      val description: Column<String> = text("description")

      val release_year: Column<Int> = integer("release_year")

      val language_id: Column<Int> = integer("language_id")

      val rental_duration: Column<Int> = integer("rental_duration")

      val rental_rate: Column<Double> = double("rental_rate")

      val length: Column<Int> = integer("length")

      val replacement_cost: Column<Double> = double("replacement_cost")

      val rating: Column<String> = text("rating")

      val last_update: Column<String> = text("last_update")

      val special_features: Column<String> = text("special_features")

      val fulltext: Column<String> = text("fulltext")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.film {
      override var film_id: Int by Table.film_id

      override var title: String by Table.title

      override var description: String by Table.description

      override var release_year: Int by Table.release_year

      override var language_id: Int by Table.language_id

      override var rental_duration: Int by Table.rental_duration

      override var rental_rate: Double by Table.rental_rate

      override var length: Int by Table.length

      override var replacement_cost: Double by Table.replacement_cost

      override var rating: String by Table.rating

      override var last_update: String by Table.last_update

      override var special_features: String by Table.special_features

      override var fulltext: String by Table.fulltext

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.film) {
          Entity.new {
            film_id = source.film_id
            title = source.title
            description = source.description
            release_year = source.release_year
            language_id = source.language_id
            rental_duration = source.rental_duration
            rental_rate = source.rental_rate
            length = source.length
            replacement_cost = source.replacement_cost
            rating = source.rating
            last_update = source.last_update
            special_features = source.special_features
            fulltext = source.fulltext
          }
        }
      }
    }
  }

  object film_actor {
    fun create(source: ResultRow) = DvdRentalDto.film_actor(source[Table.actor_id],
        source[Table.film_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("film_actor") {
      val actor_id: Column<Int> = integer("actor_id")

      val film_id: Column<Int> = integer("film_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.film_actor {
      override var actor_id: Int by Table.actor_id

      override var film_id: Int by Table.film_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.film_actor) {
          Entity.new {
            actor_id = source.actor_id
            film_id = source.film_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object film_category {
    fun create(source: ResultRow) = DvdRentalDto.film_category(source[Table.film_id],
        source[Table.category_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("film_category") {
      val film_id: Column<Int> = integer("film_id")

      val category_id: Column<Int> = integer("category_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.film_category {
      override var film_id: Int by Table.film_id

      override var category_id: Int by Table.category_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.film_category) {
          Entity.new {
            film_id = source.film_id
            category_id = source.category_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object inventory {
    fun create(source: ResultRow) = DvdRentalDto.inventory(source[Table.inventory_id],
        source[Table.film_id], source[Table.store_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("inventory") {
      val inventory_id: Column<Int> = integer("inventory_id")

      val film_id: Column<Int> = integer("film_id")

      val store_id: Column<Int> = integer("store_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.inventory {
      override var inventory_id: Int by Table.inventory_id

      override var film_id: Int by Table.film_id

      override var store_id: Int by Table.store_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.inventory) {
          Entity.new {
            inventory_id = source.inventory_id
            film_id = source.film_id
            store_id = source.store_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object language {
    fun create(source: ResultRow) = DvdRentalDto.language(source[Table.language_id],
        source[Table.name], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("language") {
      val language_id: Column<Int> = integer("language_id")

      val name: Column<String> = text("name")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.language {
      override var language_id: Int by Table.language_id

      override var name: String by Table.name

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.language) {
          Entity.new {
            language_id = source.language_id
            name = source.name
            last_update = source.last_update
          }
        }
      }
    }
  }

  object payment {
    fun create(source: ResultRow) = DvdRentalDto.payment(source[Table.payment_id],
        source[Table.customer_id], source[Table.staff_id], source[Table.rental_id],
        source[Table.amount], source[Table.payment_date])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("payment") {
      val payment_id: Column<Int> = integer("payment_id")

      val customer_id: Column<Int> = integer("customer_id")

      val staff_id: Column<Int> = integer("staff_id")

      val rental_id: Column<Int> = integer("rental_id")

      val amount: Column<Double> = double("amount")

      val payment_date: Column<String> = text("payment_date")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.payment {
      override var payment_id: Int by Table.payment_id

      override var customer_id: Int by Table.customer_id

      override var staff_id: Int by Table.staff_id

      override var rental_id: Int by Table.rental_id

      override var amount: Double by Table.amount

      override var payment_date: String by Table.payment_date

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.payment) {
          Entity.new {
            payment_id = source.payment_id
            customer_id = source.customer_id
            staff_id = source.staff_id
            rental_id = source.rental_id
            amount = source.amount
            payment_date = source.payment_date
          }
        }
      }
    }
  }

  object rental {
    fun create(source: ResultRow) = DvdRentalDto.rental(source[Table.rental_id],
        source[Table.rental_date], source[Table.inventory_id], source[Table.customer_id],
        source[Table.return_date], source[Table.staff_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("rental") {
      val rental_id: Column<Int> = integer("rental_id")

      val rental_date: Column<Int> = integer("rental_date")

      val inventory_id: Column<Int> = integer("inventory_id")

      val customer_id: Column<Int> = integer("customer_id")

      val return_date: Column<String> = text("return_date")

      val staff_id: Column<Int> = integer("staff_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.rental {
      override var rental_id: Int by Table.rental_id

      override var rental_date: Int by Table.rental_date

      override var inventory_id: Int by Table.inventory_id

      override var customer_id: Int by Table.customer_id

      override var return_date: String by Table.return_date

      override var staff_id: Int by Table.staff_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.rental) {
          Entity.new {
            rental_id = source.rental_id
            rental_date = source.rental_date
            inventory_id = source.inventory_id
            customer_id = source.customer_id
            return_date = source.return_date
            staff_id = source.staff_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object staff {
    fun create(source: ResultRow) = DvdRentalDto.staff(source[Table.staff_id],
        source[Table.first_name], source[Table.last_name], source[Table.address_id],
        source[Table.email], source[Table.store_id], source[Table.active], source[Table.username],
        source[Table.password], source[Table.last_update], source[Table.picture])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("staff") {
      val staff_id: Column<Int> = integer("staff_id")

      val first_name: Column<Int> = integer("first_name")

      val last_name: Column<Int> = integer("last_name")

      val address_id: Column<Int> = integer("address_id")

      val email: Column<Int> = integer("email")

      val store_id: Column<Int> = integer("store_id")

      val active: Column<Int> = integer("active")

      val username: Column<String> = text("username")

      val password: Column<String> = text("password")

      val last_update: Column<String> = text("last_update")

      val picture: Column<Int> = integer("picture")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.staff {
      override var staff_id: Int by Table.staff_id

      override var first_name: Int by Table.first_name

      override var last_name: Int by Table.last_name

      override var address_id: Int by Table.address_id

      override var email: Int by Table.email

      override var store_id: Int by Table.store_id

      override var active: Int by Table.active

      override var username: String by Table.username

      override var password: String by Table.password

      override var last_update: String by Table.last_update

      override var picture: Int by Table.picture

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.staff) {
          Entity.new {
            staff_id = source.staff_id
            first_name = source.first_name
            last_name = source.last_name
            address_id = source.address_id
            email = source.email
            store_id = source.store_id
            active = source.active
            username = source.username
            password = source.password
            last_update = source.last_update
            picture = source.picture
          }
        }
      }
    }
  }

  object store {
    fun create(source: ResultRow) = DvdRentalDto.store(source[Table.store_id],
        source[Table.manager_staff_id], source[Table.address_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("store") {
      val store_id: Column<Int> = integer("store_id")

      val manager_staff_id: Column<Int> = integer("manager_staff_id")

      val address_id: Column<Int> = integer("address_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.store {
      override var store_id: Int by Table.store_id

      override var manager_staff_id: Int by Table.manager_staff_id

      override var address_id: Int by Table.address_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.store) {
          Entity.new {
            store_id = source.store_id
            manager_staff_id = source.manager_staff_id
            address_id = source.address_id
            last_update = source.last_update
          }
        }
      }
    }
  }
}
