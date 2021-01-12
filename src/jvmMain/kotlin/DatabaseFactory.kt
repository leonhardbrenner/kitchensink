import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.db.JohnnySeedsDb
import model.db.JohnnySeedsDb.DetailedSeeds.maturity
import model.db.JohnnySeedsDb.DetailedSeeds.name
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import services.JohnnySeedsService
import model.db.JohnnySeedsDb.DetailedSeeds
import model.db.JohnnySeedsDb.Seed

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

            SchemaUtils.drop(DetailedSeeds)
            SchemaUtils.create (DetailedSeeds)
            JohnnySeedsService.DetailedSeed().fromFile().forEach { seed ->
                // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
                val stPete = JohnnySeedsDb.Seed.new {
                    name = seed.name
                    maturity = seed.maturity
                    secondName = seed.secondary_name
                    description = seed.description
                    image = seed.image
                    link = seed.link
                }
                println("Creating ${seed.name}")
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