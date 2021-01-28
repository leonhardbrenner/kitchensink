import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            SchemaUtils.drop(JohnnySeedsDb.DetailedSeeds.Table)
            SchemaUtils.create (JohnnySeedsDb.DetailedSeeds.Table)
            val seeds = JohnnySeedsService(kMapper).DetailedSeed().fromFile()
            seeds.forEach { row ->
                JohnnySeedsDb.DetailedSeeds.Entity.new {
                    name = row.name
                    maturity = row.maturity
                    secondary_name = row.secondary_name
                    description = row.description
                    image = row.image
                    link = row.link
                }
                //TODO - see if there is any advantage to this I prefer using Seed.new.
                //JohnnySeedsDb.DetailedSeed.Table.insert { record ->
                //    record[name] =row.name
                //    record[maturity] = row.maturity
                //    record[secondName] = row.secondary_name
                //    record[description] = row.description
                //    record[image] = row.image
                //    record[link] = row.link
                //}
                println("Creating ${row.name}")
            }

            SchemaUtils.drop(JohnnySeedsDb.Category.Table)
            SchemaUtils.create (JohnnySeedsDb.Category.Table)
            val categories = JohnnySeedsService(kMapper).Category().fromFile()
            categories.forEach {
                JohnnySeedsDb.Category.Entity.new {
                    name = it.name
                    image = it.image
                    link = it.link
                }
                println("Creating ${it.name}")
            }

            SchemaUtils.drop(JohnnySeedsDb.BasicSeed.Table)
            SchemaUtils.create (JohnnySeedsDb.BasicSeed.Table)
            val basicSeeds = JohnnySeedsService(kMapper).BasicSeed()
            basicSeeds.fromFile().forEach {
                JohnnySeedsDb.BasicSeed.Entity.new {
                    name = it.name
                    secondary_name = it.secondary_name
                    description = it.description
                    image = it.image
                    link = it.link
                }
                println("Creating ${it.name}")
            }

            SchemaUtils.drop(JohnnySeedsDb.SeedFacts.Table)
            SchemaUtils.create (JohnnySeedsDb.SeedFacts.Table)
            val seedFacts = JohnnySeedsService(kMapper).SeedFacts()
            //XXX - bring this back when you get List<> working.
            //seedFacts.fromFile().forEach {
            //    JohnnySeedsDb.SeedFacts.Entity.new {
            //        name = it.name
            //        facts = ""//it.facts!!//[0] //TODO - should be list in DB as well
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