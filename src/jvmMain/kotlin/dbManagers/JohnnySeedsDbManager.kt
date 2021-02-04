package dbManagers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import generated.model.DvdRentalCsvLoader
import generated.model.JohnnySeedsDto
import generated.model.db.DvdRentalDb
import generated.model.db.JohnnySeedsDb
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
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

object JohnnySeedsDBManager {
    val kMapper = ObjectMapper().registerModule(KotlinModule())

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
        //JohnnySeedsService(getKMapper).seedFacts.forEach { //source ->
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
