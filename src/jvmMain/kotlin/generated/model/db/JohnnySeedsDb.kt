package generated.model.db

import generated.model.JohnnySeeds
import generated.model.JohnnySeedsDto
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

object JohnnySeedsDb {
  object DetailedSeeds {
    fun create(source: ResultRow) = JohnnySeedsDto.DetailedSeeds(source[Table.name],
        source[Table.maturity], source[Table.secondary_name], source[Table.description],
        source[Table.image], source[Table.link])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("DetailedSeeds") {
      val name: Column<String> = text("name")

      val maturity: Column<String?> = text("maturity").nullable()

      val secondary_name: Column<String?> = text("secondary_name").nullable()

      val description: Column<String?> = text("description").nullable()

      val image: Column<String?> = text("image").nullable()

      val link: Column<String?> = text("link").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.JohnnySeeds.DetailedSeeds {
      override var name: String by Table.name

      override var maturity: String? by Table.maturity

      override var secondary_name: String? by Table.secondary_name

      override var description: String? by Table.description

      override var image: String? by Table.image

      override var link: String? by Table.link

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: JohnnySeeds.DetailedSeeds) {
          Entity.new {
            name = source.name
            maturity = source.maturity
            secondary_name = source.secondary_name
            description = source.description
            image = source.image
            link = source.link
          }
        }
      }
    }
  }

  object Category {
    fun create(source: ResultRow) = JohnnySeedsDto.Category(source[Table.name], source[Table.image],
        source[Table.link])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("Category") {
      val name: Column<String> = text("name")

      val image: Column<String> = text("image")

      val link: Column<String> = text("link")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.JohnnySeeds.Category {
      override var name: String by Table.name

      override var image: String by Table.image

      override var link: String by Table.link

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: JohnnySeeds.Category) {
          Entity.new {
            name = source.name
            image = source.image
            link = source.link
          }
        }
      }
    }
  }

  object BasicSeed {
    fun create(source: ResultRow) = JohnnySeedsDto.BasicSeed(source[Table.name],
        source[Table.secondary_name], source[Table.description], source[Table.image],
        source[Table.link])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("BasicSeed") {
      val name: Column<String> = text("name")

      val secondary_name: Column<String> = text("secondary_name")

      val description: Column<String?> = text("description").nullable()

      val image: Column<String> = text("image")

      val link: Column<String> = text("link")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.JohnnySeeds.BasicSeed {
      override var name: String by Table.name

      override var secondary_name: String by Table.secondary_name

      override var description: String? by Table.description

      override var image: String by Table.image

      override var link: String by Table.link

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: JohnnySeeds.BasicSeed) {
          Entity.new {
            name = source.name
            secondary_name = source.secondary_name
            description = source.description
            image = source.image
            link = source.link
          }
        }
      }
    }
  }

  object SeedFacts {
    fun create(source: ResultRow) = JohnnySeedsDto.SeedFacts(source[Table.name],
        source[Table.facts], source[Table.maturity])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("SeedFacts") {
      val name: Column<String> = text("name")

      val facts: Column<String?> = text("facts").nullable()

      val maturity: Column<String?> = text("maturity").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.JohnnySeeds.SeedFacts {
      override var name: String by Table.name

      override var facts: String? by Table.facts

      override var maturity: String? by Table.maturity

      companion object : IntEntityClass<Entity>(Table) {
        fun create(source: JohnnySeeds.SeedFacts) {
          Entity.new {
            name = source.name
            facts = source.facts
            maturity = source.maturity
          }
        }
      }
    }
  }
}
