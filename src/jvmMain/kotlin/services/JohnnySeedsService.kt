package services

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class JohnnySeedsService {

    companion object {
        val kMapper = ObjectMapper().registerModule(KotlinModule())
    }

    class DetailedSeed {
        fun deserialize(str: String): List<JohnnySeeds.DetailedSeed> = kMapper.readValue(str)
        fun fetchAll() = transaction {
            Seeds.selectAll().map {
                JohnnySeeds.DetailedSeed(
                    it[Seeds.name],
                    it[Seeds.maturity],
                    it[Seeds.secondName],
                    it[Seeds.description],
                    it[Seeds.image],
                    it[Seeds.link]
                )
            }
        }
    }

    class Category {
        fun deserialize(str: String) = kMapper.readValue<List<JohnnySeeds.Category>>(str)
        fun fetchAll() = deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/categories.json").file).readText()
        )
    }

    class BasicSeed {
        fun deserialize(str: String) = kMapper.readValue<List<JohnnySeeds.BasicSeed>>(str)
        fun fetchAll() = deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/basic-seeds.json").file).readText()
        )
    }

    class SeedFacts {
        fun deserialize(str: String) = kMapper.readValue<List<JohnnySeeds.SeedFacts>>(str)
        fun fetch() = deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/strawberry-seeds.json").file).readText()
        )[0]
    }
}

fun main(args: Array<String>) {
    val details = JohnnySeedsService.DetailedSeed().fetchAll()
    val categories = JohnnySeedsService.Category().fetchAll()
    val basicSeed = JohnnySeedsService.BasicSeed().fetchAll()
    val seed = JohnnySeedsService.SeedFacts().fetch()
    println(details[0])
    println(categories[0])
    println(basicSeed[0])
    println(seed)
}
