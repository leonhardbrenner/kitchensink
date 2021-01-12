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

    fun init() {
        Database.connect(hikari())
        //TODO - no migration taking place even though there is a migration in commonMain
        //val flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPassword).load()
        //flyway.migrate()
        //For now I am just loading some sample data
        transaction {
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            SchemaUtils.drop(JohnnySeedsDb.DetailedSeeds)
            SchemaUtils.create (JohnnySeedsDb.DetailedSeeds)
            JohnnySeedsService.DetailedSeed().fromFile().forEach {
                // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
                val stPete = JohnnySeedsDb.DetailedSeed.new {
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

            SchemaUtils.drop(JohnnySeedsDb.Categories)
            SchemaUtils.create (JohnnySeedsDb.Categories)
            JohnnySeedsService.Category().fromFile().forEach {
                // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
                val stPete = JohnnySeedsDb.Category.new {
                    name = it.name
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