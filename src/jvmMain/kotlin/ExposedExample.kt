import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import services.JohnnySeedsService

//TODO - this should show you how to connect to the database
object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property("db.jdbcUrl").getString()
    private val dbUser = appConfig.property("db.dbUser").getString()
    private val dbPassword = appConfig.property("db.dbPassword").getString()

    fun init() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

}

//TODO - Look into this article
//https://www.thebookofjoel.com/kotlin-ktor-exposed-postgres
fun main(args: Array<String>) {
    //an example connection to H2 DB
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        SchemaUtils.create (Seeds)

        JohnnySeedsService.DetailedSeed().fetchAll().forEach { seed ->
            // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
            val stPete = Seed.new {
                name = seed.name
                maturity = seed.maturity
                secondName = seed.secondary_name
            }
            //// insert new city. SQL: INSERT INTO Cities (name) VALUES ('Philly')
            //val phillyId = Seeds.insert {
            //    it[name] = ""
            //    it[name] = "Philly"
            //} get Seeds.id
        }
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
