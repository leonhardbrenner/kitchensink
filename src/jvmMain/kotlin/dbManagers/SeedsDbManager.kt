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

    val categories: List<SeedsDto.SeedCategory>
        get() = kMapper.readValue(
            resourceText("seeds/categories.json")
        )

    val detailedSeeds: List<SeedsDto.DetailedSeed>
        get() = kMapper.readValue(
            resourceText("seeds/detailed-seeds.json")
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
        SchemaUtils.drop(SeedsDb.DetailedSeed.Table)
        SchemaUtils.drop(SeedsDb.SeedCategory.Table)
        SchemaUtils.drop(SeedsDb.BasicSeed.Table)
        SchemaUtils.drop(SeedsDb.SeedFacts.Table)
    }
    fun create() = transaction {
        SchemaUtils.create(SeedsDb.DetailedSeed.Table)
        SchemaUtils.create(SeedsDb.SeedCategory.Table)
        SchemaUtils.create(SeedsDb.BasicSeed.Table)
        SchemaUtils.create(SeedsDb.SeedFacts.Table)
    }
    fun populate() = transaction {
        SeedsService(kMapper).detailedSeeds
            .forEach { source ->
                SeedsDb.DetailedSeed.Entity.insert(source)
                println("Creating ${source.name}")
            }
        SeedsService(kMapper).categories
            .forEach { source ->
                SeedsDb.SeedCategory.Entity.insert(source)
                println("Creating ${source.name}")
            }
        SeedsService(kMapper).basicSeeds.forEach { source ->
            SeedsDb.BasicSeed.Entity.insert(source)
            println("Creating ${source.name}")
        }
        //XXX - bring this back when you get List<> working.
        //SeedsService(getKMapper).seedFacts.forEach { //source ->
        //    SeedsDb.SeedFacts.Entity.create(source)
        //}
    }
}
