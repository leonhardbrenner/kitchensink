package model.db

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow


class JohnnySeedsDb {
    object DetailedSeeds : IntIdTable() {
        val name = varchar("name", 50)
        val maturity = text("maturity").nullable()
        val secondName = text("secondName").nullable()
        val description = text("description").nullable()
        val image = text("image").nullable()
        val link = text("link").nullable()
        fun create(it: ResultRow) = JohnnySeeds.DetailedSeed(
                it[name], it[maturity], it[secondName], it[description], it[image], it[link]
        )

    }

    class DetailedSeed(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DetailedSeed>(DetailedSeeds)

        var name by DetailedSeeds.name
        var maturity by DetailedSeeds.maturity
        var secondName by DetailedSeeds.secondName
        var description by DetailedSeeds.description
        var image by DetailedSeeds.image
        var link by DetailedSeeds.link
    }

    object Categories : IntIdTable() {
        val name = text("name")
        val image = text("image")
        val link = text("link")
        fun create(it: ResultRow) = JohnnySeeds.Category(
                it[name], it[image], it[link]
        )
    }

    class Category(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Category>(Categories)

        var name by Categories.name
        var image by Categories.image
        var link by Categories.link
    }

    @Serializable
    data class BasicSeed(
            val name: String,
            val secondary_name: String,
            val description: String?,
            val image: String,
            val link: String
    )

    data class SeedFacts(
            val name: String,
            val facts: List<String>?,
            val maturity: String?
    )
}
