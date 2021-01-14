import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.db.JohnnySeedsDb
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import services.JohnnySeedsService

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property("db.jdbcUrl").getString()
    private val dbUser = appConfig.property("db.dbUser").getString()
    private val dbPassword = appConfig.property("db.dbPassword").getString()
    val kMapper = ObjectMapper().registerModule(KotlinModule())

    fun init() {
        Database.connect(hikari())
        //TODO - no migration taking place even though there is a migration in commonMain
        //val flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPassword).load()
        //flyway.migrate()
        //For now I am just loading some sample data
        transaction {
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            SchemaUtils.drop(JohnnySeedsDb.DetailedSeed.Table)
            SchemaUtils.create (JohnnySeedsDb.DetailedSeed.Table)
            val seeds = JohnnySeedsService(kMapper).DetailedSeed().fromFile()
            seeds.forEach {
                // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
                val stPete = JohnnySeedsDb.DetailedSeed.Entity.new {
                    name = it.name
                    maturity = it.maturity
                    secondName = it.secondary_name
                    description = it.description
                    image = it.image
                    link = it.link
                }
                println("Creating ${it.name}")
                //TODO - see if there is any advantage to this I prefer using Seed.new.
                //// insert new city. SQL: INSERT INTO Cities (name) VALUES ('Philly')
                //val phillyId = Seeds.insert {
                //    it[name] = ""
                //    it[name] = "Philly"
                //} get Seeds.id
            }

            //SchemaUtils.drop(JohnnySeedsDb.Category.Table)
            //SchemaUtils.create (JohnnySeedsDb.Category.Table)
            //JohnnySeedsService.Category().fromFile().forEach {
            //    // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
            //    val stPete = JohnnySeedsDb.Category.Entity.new {
            //        name = it.name
            //        image = it.image!!
            //        link = it.link!!
            //    }
            //    println("Creating ${it.name}")
            //}
            //
            //SchemaUtils.drop(JohnnySeedsDb.BasicSeed.Table)
            //SchemaUtils.create (JohnnySeedsDb.BasicSeed.Table)
            //JohnnySeedsService.BasicSeed().fromFile().forEach {
            //    // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
            //    val stPete = JohnnySeedsDb.BasicSeed.Entity.new {
            //        name = it.name
            //        secondary_name = it.secondary_name!!
            //        description = it.description
            //        image = it.image!!
            //        link = it.link!!
            //    }
            //    println("Creating ${it.name}")
            //}
            //
            //SchemaUtils.drop(JohnnySeedsDb.SeedFacts.Table)
            //SchemaUtils.create (JohnnySeedsDb.SeedFacts.Table)
            //JohnnySeedsService.SeedFacts().fromFile().forEach {
            //    // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
            //    val stPete = JohnnySeedsDb.SeedFacts.Entity.new {
            //        name = it.name
            //        facts = it.facts!!
            //        maturity = it.maturity!!
            //
            //    }
            //    println("Creating ${it.name}")
            //}

            commit()
        }
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

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

}