import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import generated.model.db.JohnnySeedsDb

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//https://www.thebookofjoel.com/kotlin-ktor-exposed-postgres
object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property("db.jdbcUrl").getString()
    private val dbUser = appConfig.property("db.dbUser").getString()
    private val dbPassword = appConfig.property("db.dbPassword").getString()

    fun init() {
        Database.connect(hikari())
        dbManagers.JohnnySeedsDBManager.apply { drop(); create(); populate() }
        dbManagers.DvdRentalDBManager.apply { drop(); create(); populate() }
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

fun main(args: Array<String>) {
    //DatabaseFactory.init()

    transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        //'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
        println("Seeds:")
        JohnnySeedsDb.DetailedSeeds.Entity.all().forEach { seed ->
            println("\t${seed.name} - ${seed.maturity}")
        }
    }

}
