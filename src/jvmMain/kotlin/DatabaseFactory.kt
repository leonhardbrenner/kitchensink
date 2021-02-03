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
import com.fasterxml.jackson.module.kotlin.readValue
import generated.model.JohnnySeedsDto
import java.io.File
import javax.inject.Inject

fun resource(path: String) = File(ClassLoader.getSystemResource(path).file)
fun resourceText(path: String) = resource(path).readText()

class JohnnySeedsService @Inject constructor(val kMapper: ObjectMapper) {

    //TODO - get this working
    inline fun <reified T> fromJson(path: String): List<T> = kMapper.readValue(
        File(ClassLoader.getSystemResource(path).file).readText()
    )

    val detailedSeeds: List<JohnnySeedsDto.DetailedSeeds>
        get() = kMapper.readValue(
            resourceText("johnnyseeds/detailed-seeds.json")
        )

    val categories: List<JohnnySeedsDto.Category>
        get() = kMapper.readValue(
            resourceText("johnnyseeds/categories.json")
        )

    val basicSeeds: List<JohnnySeedsDto.BasicSeed>
        get() = kMapper.readValue(
            resourceText("johnnyseeds/basic-seeds.json")
        )

    val seedFacts: List<JohnnySeedsDto.SeedFacts>
        get() = kMapper.readValue(
            resourceText("johnnyseeds/strawberry-seeds.json")
        )

}

//object JohnnySeedsApplication: AbstractModule() {
//
//    override fun configure() {
//        DatabaseFactory.init()
//    }
//
//}

object DatabaseFactory {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property("db.jdbcUrl").getString()
    private val dbUser = appConfig.property("db.dbUser").getString()
    private val dbPassword = appConfig.property("db.dbPassword").getString()
    val kMapper = ObjectMapper().registerModule(KotlinModule())

    fun init() {
        Database.connect(hikari())
        JohnnySeedsDBManager.apply { drop(); create(); populate() }
        DvdRentalDBManager.apply { drop(); create(); populate() }
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

object JohnnySeedsDBManager {
    fun drop() = transaction {
        SchemaUtils.drop(JohnnySeedsDb.DetailedSeeds.Table)
        SchemaUtils.drop(JohnnySeedsDb.Category.Table)
        SchemaUtils.drop(JohnnySeedsDb.BasicSeed.Table)
        SchemaUtils.drop(JohnnySeedsDb.SeedFacts.Table)
    }
    fun create() = transaction {
        SchemaUtils.create(JohnnySeedsDb.DetailedSeeds.Table)
        SchemaUtils.create(JohnnySeedsDb.Category.Table)
        SchemaUtils.create(JohnnySeedsDb.BasicSeed.Table)
        SchemaUtils.create(JohnnySeedsDb.SeedFacts.Table)
    }
    fun populate() = transaction {
        JohnnySeedsService(kMapper).detailedSeeds
            .forEach { source ->
                JohnnySeedsDb.DetailedSeeds.Entity.create(source)
                println("Creating ${source.name}")
            }
        JohnnySeedsService(kMapper).categories
            .forEach { source ->
                JohnnySeedsDb.Category.Entity.create(source)
                println("Creating ${source.name}")
            }
        JohnnySeedsService(kMapper).basicSeeds.forEach { source ->
            JohnnySeedsDb.BasicSeed.Entity.create(source)
            println("Creating ${source.name}")
        }
        //XXX - bring this back when you get List<> working.
        //JohnnySeedsService(kMapper).seedFacts.forEach { //source ->
        //    JohnnySeedsDb.SeedFacts.Entity.create(source)
        //}
    }
}

//Look across at dvd rentals the copy * from should be handled as a dsl off which loads
//the values in the order they appear in the type and inserts them just as we did above but this
//we are loading from a DSL. This seems like a good time to clean up code above. Also don't
//use x everywhere. Play tribute to XmlSchema and go SimpleType and ComplexType. :+2
object DvdRentalDBManager {
    fun drop() = transaction {
        SchemaUtils.drop(DvdRentalDb.actor.Table)
        SchemaUtils.drop(DvdRentalDb.address.Table)
    }
    fun create() = transaction {
        SchemaUtils.create (DvdRentalDb.actor.Table)
        SchemaUtils.create (DvdRentalDb.address.Table)
    }
    fun populate() = transaction {
        DvdRentalCsvLoader.actor.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3057.dat").forEach { source ->
            DvdRentalDb.actor.Entity.create(source)
            println("Creating ${source.first_name} ${source.last_name}")
        }
        DvdRentalCsvLoader.address.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3065.dat").forEach { source ->
            DvdRentalDb.address.Entity.create(source)
            println("Creating ${source.address} ${source.phone}")
        }
    }
}
