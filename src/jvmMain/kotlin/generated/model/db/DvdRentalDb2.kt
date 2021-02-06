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

object DvdRentalDb2 {
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
    fun create(source: ResultRow) = DvdRentalDto.address(source[Table.address],
        source[Table.address2], source[Table.address_id], source[Table.city_id],
        source[Table.district], source[Table.last_update], source[Table.phone],
        source[Table.postal_code])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("address") {
      val address: Column<String> = text("address")

      val address2: Column<String?> = text("address2").nullable()

      val address_id: Column<Int> = integer("address_id")

      val city_id: Column<Int> = integer("city_id")

      val district: Column<String?> = text("district").nullable()

      val last_update: Column<String> = text("last_update")

      val phone: Column<String?> = text("phone").nullable()

      val postal_code: Column<String?> = text("postal_code").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.address {
      override var address: String by Table.address

      override var address2: String? by Table.address2

      override var address_id: Int by Table.address_id

      override var city_id: Int by Table.city_id

      override var district: String? by Table.district

      override var last_update: String by Table.last_update

      override var phone: String? by Table.phone

      override var postal_code: String? by Table.postal_code

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.address) {
          Entity.new {
            address = source.address
            address2 = source.address2
            address_id = source.address_id
            city_id = source.city_id
            district = source.district
            last_update = source.last_update
            phone = source.phone
            postal_code = source.postal_code
          }
        }
      }
    }
  }

  object category {
    fun create(source: ResultRow) = DvdRentalDto.category(source[Table.category_id],
        source[Table.last_update], source[Table.name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("category") {
      val category_id: Column<Int> = integer("category_id")

      val last_update: Column<String> = text("last_update")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.category {
      override var category_id: Int by Table.category_id

      override var last_update: String by Table.last_update

      override var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.category) {
          Entity.new {
            category_id = source.category_id
            last_update = source.last_update
            name = source.name
          }
        }
      }
    }
  }

  object city {
    fun create(source: ResultRow) = DvdRentalDto.city(source[Table.city], source[Table.city_id],
        source[Table.country_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("city") {
      val city: Column<String> = text("city")

      val city_id: Column<Int> = integer("city_id")

      val country_id: Column<Int> = integer("country_id")

      val last_update: Column<Int> = integer("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.city {
      override var city: String by Table.city

      override var city_id: Int by Table.city_id

      override var country_id: Int by Table.country_id

      override var last_update: Int by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.city) {
          Entity.new {
            city = source.city
            city_id = source.city_id
            country_id = source.country_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object country {
    fun create(source: ResultRow) = DvdRentalDto.country(source[Table.country],
        source[Table.country_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("country") {
      val country: Column<String> = text("country")

      val country_id: Column<Int> = integer("country_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.country {
      override var country: String by Table.country

      override var country_id: Int by Table.country_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.country) {
          Entity.new {
            country = source.country
            country_id = source.country_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object customer {
    fun create(source: ResultRow) = DvdRentalDto.customer(source[Table.active],
        source[Table.activebool], source[Table.address_id], source[Table.create_date],
        source[Table.customer_id], source[Table.email], source[Table.first_name],
        source[Table.last_name], source[Table.last_update], source[Table.store_id])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("customer") {
      val active: Column<Int> = integer("active")

      val activebool: Column<Boolean> = bool("activebool")

      val address_id: Column<Int> = integer("address_id")

      val create_date: Column<String> = text("create_date")

      val customer_id: Column<Int> = integer("customer_id")

      val email: Column<String> = text("email")

      val first_name: Column<String> = text("first_name")

      val last_name: Column<String> = text("last_name")

      val last_update: Column<String> = text("last_update")

      val store_id: Column<Int> = integer("store_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.customer {
      override var active: Int by Table.active

      override var activebool: Boolean by Table.activebool

      override var address_id: Int by Table.address_id

      override var create_date: String by Table.create_date

      override var customer_id: Int by Table.customer_id

      override var email: String by Table.email

      override var first_name: String by Table.first_name

      override var last_name: String by Table.last_name

      override var last_update: String by Table.last_update

      override var store_id: Int by Table.store_id

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.customer) {
          Entity.new {
            active = source.active
            activebool = source.activebool
            address_id = source.address_id
            create_date = source.create_date
            customer_id = source.customer_id
            email = source.email
            first_name = source.first_name
            last_name = source.last_name
            last_update = source.last_update
            store_id = source.store_id
          }
        }
      }
    }
  }

  object film {
    fun create(source: ResultRow) = DvdRentalDto.film(source[Table.description],
        source[Table.film_id], source[Table.fulltext], source[Table.language_id],
        source[Table.last_update], source[Table.length], source[Table.rating],
        source[Table.release_year], source[Table.rental_duration], source[Table.rental_rate],
        source[Table.replacement_cost], source[Table.special_features], source[Table.title])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("film") {
      val description: Column<String> = text("description")

      val film_id: Column<Int> = integer("film_id")

      val fulltext: Column<String> = text("fulltext")

      val language_id: Column<Int> = integer("language_id")

      val last_update: Column<String> = text("last_update")

      val length: Column<Int> = integer("length")

      val rating: Column<String> = text("rating")

      val release_year: Column<Int> = integer("release_year")

      val rental_duration: Column<Int> = integer("rental_duration")

      val rental_rate: Column<Double> = double("rental_rate")

      val replacement_cost: Column<Double> = double("replacement_cost")

      val special_features: Column<String> = text("special_features")

      val title: Column<String> = text("title")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.film {
      override var description: String by Table.description

      override var film_id: Int by Table.film_id

      override var fulltext: String by Table.fulltext

      override var language_id: Int by Table.language_id

      override var last_update: String by Table.last_update

      override var length: Int by Table.length

      override var rating: String by Table.rating

      override var release_year: Int by Table.release_year

      override var rental_duration: Int by Table.rental_duration

      override var rental_rate: Double by Table.rental_rate

      override var replacement_cost: Double by Table.replacement_cost

      override var special_features: String by Table.special_features

      override var title: String by Table.title

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.film) {
          Entity.new {
            description = source.description
            film_id = source.film_id
            fulltext = source.fulltext
            language_id = source.language_id
            last_update = source.last_update
            length = source.length
            rating = source.rating
            release_year = source.release_year
            rental_duration = source.rental_duration
            rental_rate = source.rental_rate
            replacement_cost = source.replacement_cost
            special_features = source.special_features
            title = source.title
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
    fun create(source: ResultRow) = DvdRentalDto.film_category(source[Table.category_id],
        source[Table.film_id], source[Table.last_update])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("film_category") {
      val category_id: Column<Int> = integer("category_id")

      val film_id: Column<Int> = integer("film_id")

      val last_update: Column<String> = text("last_update")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.film_category {
      override var category_id: Int by Table.category_id

      override var film_id: Int by Table.film_id

      override var last_update: String by Table.last_update

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.film_category) {
          Entity.new {
            category_id = source.category_id
            film_id = source.film_id
            last_update = source.last_update
          }
        }
      }
    }
  }

  object inventory {
    fun create(source: ResultRow) = DvdRentalDto.inventory(source[Table.film_id],
        source[Table.inventory_id], source[Table.last_update], source[Table.store_id])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("inventory") {
      val film_id: Column<Int> = integer("film_id")

      val inventory_id: Column<Int> = integer("inventory_id")

      val last_update: Column<String> = text("last_update")

      val store_id: Column<Int> = integer("store_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.inventory {
      override var film_id: Int by Table.film_id

      override var inventory_id: Int by Table.inventory_id

      override var last_update: String by Table.last_update

      override var store_id: Int by Table.store_id

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.inventory) {
          Entity.new {
            film_id = source.film_id
            inventory_id = source.inventory_id
            last_update = source.last_update
            store_id = source.store_id
          }
        }
      }
    }
  }

  object language {
    fun create(source: ResultRow) = DvdRentalDto.language(source[Table.language_id],
        source[Table.last_update], source[Table.name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("language") {
      val language_id: Column<Int> = integer("language_id")

      val last_update: Column<String> = text("last_update")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.language {
      override var language_id: Int by Table.language_id

      override var last_update: String by Table.last_update

      override var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.language) {
          Entity.new {
            language_id = source.language_id
            last_update = source.last_update
            name = source.name
          }
        }
      }
    }
  }

  object payment {
    fun create(source: ResultRow) = DvdRentalDto.payment(source[Table.amount],
        source[Table.customer_id], source[Table.payment_date], source[Table.payment_id],
        source[Table.rental_id], source[Table.staff_id])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("payment") {
      val amount: Column<Double> = double("amount")

      val customer_id: Column<Int> = integer("customer_id")

      val payment_date: Column<String> = text("payment_date")

      val payment_id: Column<Int> = integer("payment_id")

      val rental_id: Column<Int> = integer("rental_id")

      val staff_id: Column<Int> = integer("staff_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.payment {
      override var amount: Double by Table.amount

      override var customer_id: Int by Table.customer_id

      override var payment_date: String by Table.payment_date

      override var payment_id: Int by Table.payment_id

      override var rental_id: Int by Table.rental_id

      override var staff_id: Int by Table.staff_id

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.payment) {
          Entity.new {
            amount = source.amount
            customer_id = source.customer_id
            payment_date = source.payment_date
            payment_id = source.payment_id
            rental_id = source.rental_id
            staff_id = source.staff_id
          }
        }
      }
    }
  }

  object rental {
    fun create(source: ResultRow) = DvdRentalDto.rental(source[Table.customer_id],
        source[Table.inventory_id], source[Table.last_update], source[Table.rental_date],
        source[Table.rental_id], source[Table.return_date], source[Table.staff_id])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("rental") {
      val customer_id: Column<Int> = integer("customer_id")

      val inventory_id: Column<Int> = integer("inventory_id")

      val last_update: Column<String> = text("last_update")

      val rental_date: Column<Int> = integer("rental_date")

      val rental_id: Column<Int> = integer("rental_id")

      val return_date: Column<String> = text("return_date")

      val staff_id: Column<Int> = integer("staff_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.rental {
      override var customer_id: Int by Table.customer_id

      override var inventory_id: Int by Table.inventory_id

      override var last_update: String by Table.last_update

      override var rental_date: Int by Table.rental_date

      override var rental_id: Int by Table.rental_id

      override var return_date: String by Table.return_date

      override var staff_id: Int by Table.staff_id

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.rental) {
          Entity.new {
            customer_id = source.customer_id
            inventory_id = source.inventory_id
            last_update = source.last_update
            rental_date = source.rental_date
            rental_id = source.rental_id
            return_date = source.return_date
            staff_id = source.staff_id
          }
        }
      }
    }
  }

  object staff {
    fun create(source: ResultRow) = DvdRentalDto.staff(source[Table.active],
        source[Table.address_id], source[Table.email], source[Table.first_name],
        source[Table.last_name], source[Table.last_update], source[Table.password],
        source[Table.picture], source[Table.staff_id], source[Table.store_id],
        source[Table.username])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("staff") {
      val active: Column<Int> = integer("active")

      val address_id: Column<Int> = integer("address_id")

      val email: Column<Int> = integer("email")

      val first_name: Column<String> = text("first_name")

      val last_name: Column<String> = text("last_name")

      val last_update: Column<String> = text("last_update")

      val password: Column<String> = text("password")

      val picture: Column<Int> = integer("picture")

      val staff_id: Column<Int> = integer("staff_id")

      val store_id: Column<Int> = integer("store_id")

      val username: Column<String> = text("username")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.staff {
      override var active: Int by Table.active

      override var address_id: Int by Table.address_id

      override var email: Int by Table.email

      override var first_name: String by Table.first_name

      override var last_name: String by Table.last_name

      override var last_update: String by Table.last_update

      override var password: String by Table.password

      override var picture: Int by Table.picture

      override var staff_id: Int by Table.staff_id

      override var store_id: Int by Table.store_id

      override var username: String by Table.username

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.staff) {
          Entity.new {
            active = source.active
            address_id = source.address_id
            email = source.email
            first_name = source.first_name
            last_name = source.last_name
            last_update = source.last_update
            password = source.password
            picture = source.picture
            staff_id = source.staff_id
            store_id = source.store_id
            username = source.username
          }
        }
      }
    }
  }

  object store {
    fun create(source: ResultRow) = DvdRentalDto.store(source[Table.address_id],
        source[Table.last_update], source[Table.manager_staff_id], source[Table.store_id])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("store") {
      val address_id: Column<Int> = integer("address_id")

      val last_update: Column<String> = text("last_update")

      val manager_staff_id: Column<Int> = integer("manager_staff_id")

      val store_id: Column<Int> = integer("store_id")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.DvdRental.store {
      override var address_id: Int by Table.address_id

      override var last_update: String by Table.last_update

      override var manager_staff_id: Int by Table.manager_staff_id

      override var store_id: Int by Table.store_id

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: DvdRental.store) {
          Entity.new {
            address_id = source.address_id
            last_update = source.last_update
            manager_staff_id = source.manager_staff_id
            store_id = source.store_id
          }
        }
      }
    }
  }
}
