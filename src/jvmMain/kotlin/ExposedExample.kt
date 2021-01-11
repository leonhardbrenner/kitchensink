import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import services.JohnnySeedsService

//TODO - Look into this article
//https://www.thebookofjoel.com/kotlin-ktor-exposed-postgres
fun main(args: Array<String>) {
    //an example connection to H2 DB
    //Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    DatabaseFactory.init()

    transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        //'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
        println("Seeds:")
        Seed.all().forEach { seed ->
            println("\t${seed.name} - ${seed.maturity}")
        }
    }

}

object Seeds: IntIdTable() {
    val name = varchar("name", 50)
    val maturity = text("maturity").nullable()
    val secondName = varchar("secondName", 50).nullable()
    val description = text("description").nullable()
    val image = varchar("image", 50).nullable()
    val link = varchar("link", 50).nullable()
}

class Seed(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Seed>(Seeds)
    var name by Seeds.name
    var maturity by Seeds.maturity
    var secondName by Seeds.secondName
    var description by Seeds.description
    var image by Seeds.image
    var link by Seeds.link
}
