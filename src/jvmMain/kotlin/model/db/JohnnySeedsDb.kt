package model.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class JohnnySeedsDb {
    object DetailedSeeds : IntIdTable() {
        val name = varchar("name", 50)
        val maturity = text("maturity").nullable()
        val secondName = text("secondName").nullable()
        val description = text("description").nullable()
        val image = text("image").nullable()
        val link = text("link").nullable()
    }
    class Seed(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Seed>(DetailedSeeds)
        var name by DetailedSeeds.name
        var maturity by DetailedSeeds.maturity
        var secondName by DetailedSeeds.secondName
        var description by DetailedSeeds.description
        var image by DetailedSeeds.image
        var link by DetailedSeeds.link
    }
}