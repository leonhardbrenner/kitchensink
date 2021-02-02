import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import generated.model.DvdRentalCsvLoader
import generated.model.db.DvdRentalDb
import io.ktor.config.HoconApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import generated.model.db.JohnnySeedsDb
import org.jetbrains.exposed.sql.*
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
            SchemaUtils.drop(JohnnySeedsDb.DetailedSeeds.Table)
            SchemaUtils.create (JohnnySeedsDb.DetailedSeeds.Table)
            JohnnySeedsService(kMapper).DetailedSeed().fromJson()
                .forEach { source ->
                    JohnnySeedsDb.DetailedSeeds.Entity.create(source)
                    println("Creating ${source.name}")
                }

            SchemaUtils.drop(JohnnySeedsDb.Category.Table)
            SchemaUtils.create (JohnnySeedsDb.Category.Table)
            JohnnySeedsService(kMapper).Category().fromJson()
                .forEach { source ->
                    JohnnySeedsDb.Category.Entity.create(source)
                    println("Creating ${source.name}")
                }

            SchemaUtils.drop(JohnnySeedsDb.BasicSeed.Table)
            SchemaUtils.create (JohnnySeedsDb.BasicSeed.Table)
            JohnnySeedsService(kMapper).BasicSeed().fromJson().forEach { source ->
                JohnnySeedsDb.BasicSeed.Entity.create(source)
                println("Creating ${source.name}")
            }

            SchemaUtils.drop(JohnnySeedsDb.SeedFacts.Table)
            SchemaUtils.create (JohnnySeedsDb.SeedFacts.Table)
            //XXX - bring this back when you get List<> working.
            //JohnnySeedsService(kMapper).SeedFacts().fromJson().forEach { source ->
            //  JohnnySeedsDb.SeedFacts.Entity.create(source)
            //}

            commit()

            //Look across at dvd rentals the copy * from should be handled as a dsl off which loads
            //the values in the order they appear in the type and inserts them just as we did above but this
            //we are loading from a DSL. This seems like a good time to clean up code above. Also don't
            //use x everywhere. Play tribute to XmlSchema and go SimpleType and ComplexType. :+2
            SchemaUtils.drop(DvdRentalDb.actor.Table)
            SchemaUtils.create (DvdRentalDb.actor.Table)
            DvdRentalCsvLoader.actor.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3057.dat").forEach { source ->
                DvdRentalDb.actor.Entity.create(source)
                println("Creating ${source.first_name} ${source.last_name}")
            }

            SchemaUtils.drop(DvdRentalDb.address.Table)
            SchemaUtils.create (DvdRentalDb.address.Table)
            DvdRentalCsvLoader.address.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3065.dat").forEach { source ->
                DvdRentalDb.address.Entity.create(source)
                println("Creating ${source.address} ${source.phone}")
            }

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