package model.db

import JohnnySeeds
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object JohnnySeedsDb {
    object DetailedSeed {
        object Table : IntIdTable("detailed_seed") {
            val name = varchar("name", 50)
            val maturity = text("maturity").nullable()
            val secondName = text("secondName").nullable()
            val description = text("description").nullable()
            val image = text("image").nullable()
            val link = text("link").nullable()
        }
        class Entity(id: EntityID<Int>) : IntEntity(id) {
            companion object : IntEntityClass<Entity>(Table)
            var name by Table.name
            var maturity by Table.maturity
            var secondName by Table.secondName
            var description by Table.description
            var image by Table.image
            var link by Table.link
        }
        fun create(it: ResultRow) = JohnnySeedsDto.DetailedSeeds(
                it[Table.name], it[Table.maturity], it[Table.secondName], it[Table.description], it[Table.image]!!, it[Table.link]
        )
        fun fetchAll() = transaction {
            with (Table) {
                selectAll().map { create(it) }
            }
        }
    }

    object Category {
        object Table : IntIdTable("category") {
            val name = text("name")
            val image = text("image")
            val link = text("link")
        }
        class Entity(id: EntityID<Int>) : IntEntity(id) {
            companion object : IntEntityClass<Entity>(Table)
            var name by Table.name
            var image by Table.image
            var link by Table.link
        }
        fun create(it: ResultRow) = JohnnySeedsDto.Category(
                it[Table.name], it[Table.image], it[Table.link]
        )
        fun fetchAll() = transaction {
            with (Table) {
                selectAll().map { create(it) }
            }
        }

    }

    object BasicSeed {
        object Table : IntIdTable("basic_seed") {
            val name = text("name")
            val secondary_name = text("secondary_name")
            val description = text("description").nullable()
            val image = text("image")
            val link = text("link")
        }
        class Entity(id: EntityID<Int>) : IntEntity(id) {
            companion object : IntEntityClass<Entity>(Table)
            var name by Table.name
            var secondary_name by Table.secondary_name
            var description by Table.description
            var image by Table.image
            var link by Table.link
        }
        fun create(it: ResultRow) = JohnnySeedsDto.BasicSeed(
                it[Table.name], it[Table.secondary_name], it[Table.description], it[Table.image], it[Table.link]
        )
        fun fetchAll() = transaction {
            with (Table) {
                selectAll().map { create(it) }
            }
        }
    }

    object SeedFacts {
        object Table : IntIdTable("seed_facts") {
            val name = text("name")
            val facts = text("facts") //TODO - facts should be plural
            val maturity = text("maturity")
        }
        class Entity(id: EntityID<Int>) : IntEntity(id) {
            companion object : IntEntityClass<Entity>(Table)
            var name by Table.name
            var facts by Table.facts
            var maturity by Table.maturity
        }
        fun create(it: ResultRow) = JohnnySeedsDto.SeedFacts(
                it[Table.name]!!,
                it[Table.facts], //listOf(it[Table.facts])!!,  //..TODO - facts should be plural
                it[Table.maturity]
        )
        fun fetchAll() = transaction {
            with (Table) {
                selectAll().map { create(it) }
            }
        }
    }
}
