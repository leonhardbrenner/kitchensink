package generated.model.db

import generated.model.Seeds
import generated.model.SeedsDto
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

object SeedsDb {
  object BasicSeed {
    fun create(source: ResultRow) = SeedsDto.BasicSeed(source[Table.description],
        source[Table.image], source[Table.link], source[Table.name], source[Table.secondary_name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("BasicSeed") {
      val description: Column<String?> = text("description").nullable()

      val image: Column<String> = text("image")

      val link: Column<String> = text("link")

      val name: Column<String> = text("name")

      val secondary_name: Column<String> = text("secondary_name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Seeds.BasicSeed {
      override var description: String? by Table.description

      override var image: String by Table.image

      override var link: String by Table.link

      override var name: String by Table.name

      override var secondary_name: String by Table.secondary_name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: Seeds.BasicSeed) {
          Entity.new {
            description = source.description
            image = source.image
            link = source.link
            name = source.name
            secondary_name = source.secondary_name
          }
        }
      }
    }
  }

  object DetailedSeed {
    fun create(source: ResultRow) = SeedsDto.DetailedSeed(source[Table.description],
        source[Table.image], source[Table.link], source[Table.maturity], source[Table.name],
        source[Table.secondary_name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("DetailedSeed") {
      val description: Column<String?> = text("description").nullable()

      val image: Column<String?> = text("image").nullable()

      val link: Column<String?> = text("link").nullable()

      val maturity: Column<String?> = text("maturity").nullable()

      val name: Column<String> = text("name")

      val secondary_name: Column<String?> = text("secondary_name").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Seeds.DetailedSeed {
      override var description: String? by Table.description

      override var image: String? by Table.image

      override var link: String? by Table.link

      override var maturity: String? by Table.maturity

      override var name: String by Table.name

      override var secondary_name: String? by Table.secondary_name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: Seeds.DetailedSeed) {
          Entity.new {
            description = source.description
            image = source.image
            link = source.link
            maturity = source.maturity
            name = source.name
            secondary_name = source.secondary_name
          }
        }
      }
    }
  }

  object SeedCategory {
    fun create(source: ResultRow) = SeedsDto.SeedCategory(source[Table.image], source[Table.link],
        source[Table.name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("SeedCategory") {
      val image: Column<String> = text("image")

      val link: Column<String> = text("link")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Seeds.SeedCategory {
      override var image: String by Table.image

      override var link: String by Table.link

      override var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: Seeds.SeedCategory) {
          Entity.new {
            image = source.image
            link = source.link
            name = source.name
          }
        }
      }
    }
  }

  object SeedFacts {
    fun create(source: ResultRow) = SeedsDto.SeedFacts(source[Table.facts], source[Table.maturity],
        source[Table.name])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("SeedFacts") {
      val facts: Column<String?> = text("facts").nullable()

      val maturity: Column<String?> = text("maturity").nullable()

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Seeds.SeedFacts {
      override var facts: String? by Table.facts

      override var maturity: String? by Table.maturity

      override var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: Seeds.SeedFacts) {
          Entity.new {
            facts = source.facts
            maturity = source.maturity
            name = source.name
          }
        }
      }
    }
  }
}
