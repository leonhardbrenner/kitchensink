package dbManagers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import generated.model.DvdRentalCsvLoader
import generated.model.SeedsDto
import generated.model.db.DvdRentalDb
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import javax.inject.Inject

fun resource(path: String) = File(ClassLoader.getSystemResource(path).file)
fun resourceText(path: String) = resource(path).readText()

class SeedsService @Inject constructor(val kMapper: ObjectMapper) {

    //TODO - get this working
    inline fun <reified T> fromJson(path: String): List<T> = kMapper.readValue(
        File(ClassLoader.getSystemResource(path).file).readText()
    )

    val detailedSeeds: List<SeedsDto.DetailedSeeds>
        get() = kMapper.readValue(
            resourceText("seeds/detailed-seeds.json")
        )

    val categories: List<SeedsDto.Category>
        get() = kMapper.readValue(
            resourceText("seeds/categories.json")
        )

    val basicSeeds: List<SeedsDto.BasicSeed>
        get() = kMapper.readValue(
            resourceText("seeds/basic-seeds.json")
        )

    val seedFacts: List<SeedsDto.SeedFacts>
        get() = kMapper.readValue(
            resourceText("seeds/strawberry-seeds.json")
        )

}

object SeedsDBManager {
    val kMapper = ObjectMapper().registerModule(KotlinModule())

    fun drop() = transaction {
        SchemaUtils.drop(SeedsDb.DetailedSeeds.Table)
        SchemaUtils.drop(SeedsDb.Category.Table)
        SchemaUtils.drop(SeedsDb.BasicSeed.Table)
        SchemaUtils.drop(SeedsDb.SeedFacts.Table)
    }
    fun create() = transaction {
        SchemaUtils.create(SeedsDb.DetailedSeeds.Table)
        SchemaUtils.create(SeedsDb.Category.Table)
        SchemaUtils.create(SeedsDb.BasicSeed.Table)
        SchemaUtils.create(SeedsDb.SeedFacts.Table)
    }
    fun populate() = transaction {
        SeedsService(kMapper).detailedSeeds
            .forEach { source ->
                SeedsDb.DetailedSeeds.Entity.create(source)
                println("Creating ${source.name}")
            }
        SeedsService(kMapper).categories
            .forEach { source ->
                SeedsDb.Category.Entity.create(source)
                println("Creating ${source.name}")
            }
        SeedsService(kMapper).basicSeeds.forEach { source ->
            SeedsDb.BasicSeed.Entity.create(source)
            println("Creating ${source.name}")
        }
        //XXX - bring this back when you get List<> working.
        //SeedsService(getKMapper).seedFacts.forEach { //source ->
        //    SeedsDb.SeedFacts.Entity.create(source)
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
        SchemaUtils.drop(DvdRentalDb.category.Table)
    }
    fun create() = transaction {
        SchemaUtils.create (DvdRentalDb.actor.Table)
        SchemaUtils.create (DvdRentalDb.address.Table)
        SchemaUtils.create (DvdRentalDb.category.Table)
    }
    fun populate() = transaction {
        DvdRentalCsvLoader.actor.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3057.dat").forEach { source ->
            DvdRentalDb.actor.Entity.create(source)
            println("Creating ${source.firstName} ${source.lastName}")
        }
        //XXX
        //DvdRentalCsvLoader.address.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3065.dat").forEach { source ->
        //    DvdRentalDb.address.Entity.create(source)
        //    println("Creating ${source.address} ${source.phone}")
        //}
        DvdRentalCsvLoader.category.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3059.dat").forEach { source ->
            DvdRentalDb.category.Entity.create(source)
            println("Creating ${source.name}")
        }
    }
}
