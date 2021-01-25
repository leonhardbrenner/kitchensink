package services

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import javax.inject.Inject


class JohnnySeedsService @Inject constructor(val kMapper: ObjectMapper) {

    //TODO - get this working
    inline fun <reified T> fromFile(path: String): List<T> = kMapper.readValue(
            File(ClassLoader.getSystemResource(path).file).readText()
    )

    inner class DetailedSeed {
        val path = "johnnyseeds/detailed-seeds.json"
        //fun fromFile() = fromFile<JohnnySeeds.DetailedSeed>(path)
        fun fromFile(): List<JohnnySeeds.DetailedSeed.Dto> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }

    inner class Category {
        val path = "johnnyseeds/categories.json"
        fun fromFile(): List<JohnnySeeds.Category.Dto> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }

    inner class BasicSeed {
        val path = "johnnyseeds/basic-seeds.json"
        fun fromFile(): List<JohnnySeeds.BasicSeed.Dto> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }

    inner class SeedFacts {
        val path = "johnnyseeds/strawberry-seeds.json"
        fun fromFile(): List<JohnnySeeds.SeedFacts.Dto> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }
}

fun main(args: Array<String>) {
    val kMapper = ObjectMapper().registerModule(KotlinModule())
    val service = JohnnySeedsService(kMapper)
    val details = service.DetailedSeed().fromFile()
    val categories = service.Category().fromFile()
    val basicSeeds = service.BasicSeed().fromFile()
    val seed = service.SeedFacts().fromFile()
    println(details[0])
    println(categories[0])
    println(basicSeeds[0])
    println(seed)
}
