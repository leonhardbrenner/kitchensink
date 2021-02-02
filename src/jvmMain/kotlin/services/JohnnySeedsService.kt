package services

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import generated.model.JohnnySeedsDto
import java.io.File
import javax.inject.Inject


class JohnnySeedsService @Inject constructor(val kMapper: ObjectMapper) {

    //TODO - get this working
    inline fun <reified T> fromJson(path: String): List<T> = kMapper.readValue(
            File(ClassLoader.getSystemResource(path).file).readText()
    )

    inner class DetailedSeed {
        val path = "johnnyseeds/detailed-seeds.json"
        //fun fromFile() = fromFile<JohnnySeeds.DetailedSeed>(path)
        fun fromJson(): List<JohnnySeedsDto.DetailedSeeds> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }

    inner class Category {
        val path = "johnnyseeds/categories.json"
        fun fromJson(): List<JohnnySeedsDto.Category> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }

    inner class BasicSeed {
        val path = "johnnyseeds/basic-seeds.json"
        fun fromJson(): List<JohnnySeedsDto.BasicSeed> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }

    inner class SeedFacts {
        val path = "johnnyseeds/strawberry-seeds.json"
        fun fromJson(): List<JohnnySeedsDto.SeedFacts> = kMapper.readValue(
                File(ClassLoader.getSystemResource(path).file).readText()
        )
    }
}

fun main(args: Array<String>) {
    val kMapper = ObjectMapper().registerModule(KotlinModule())
    val service = JohnnySeedsService(kMapper)
    val details = service.DetailedSeed().fromJson()
    val categories = service.Category().fromJson()
    val basicSeeds = service.BasicSeed().fromJson()
    val seed = service.SeedFacts().fromJson()
    println(details[0])
    println(categories[0])
    println(basicSeeds[0])
    println(seed)
}
