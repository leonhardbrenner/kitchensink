package applications

import com.google.inject.AbstractModule
import com.mongodb.ConnectionString
import generated.model.JohnnySeedsDto
import generated.model.db.JohnnySeedsDb
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import javax.inject.Inject

class JohnnySeedsService {
    fun getDetailedSeeds() = JohnnySeedsDb.DetailedSeeds.fetchAll().subList(0, 100)
    fun getCategories() = JohnnySeedsDb.Category.fetchAll().subList(0, 100)
    fun getBasicSeeds() = JohnnySeedsDb.BasicSeed.fetchAll().subList(0, 100)
    fun getSeedFacts() = JohnnySeedsDb.SeedFacts.fetchAll().let {
        it.subList(0, if (it.size < 100) it.size else 100)
    }
}

class JohnnySeedsApplication @Inject constructor(val johnnySeedsService: JohnnySeedsService) {

    object Module : AbstractModule()

    fun routesFrom(routing: Routing) = routing.apply {
        route(JohnnySeedsDto.DetailedSeeds.path) {
            get {
                call.respond(johnnySeedsService.getDetailedSeeds())
            }
        }
        route(JohnnySeedsDto.Category.path) {
            get {
                call.respond(johnnySeedsService.getCategories())
            }
        }
        route(JohnnySeedsDto.BasicSeed.path) {
            get {
                call.respond(johnnySeedsService.getBasicSeeds())
            }
        }
        route(JohnnySeedsDto.SeedFacts.path) {
            get {
                call.respond(johnnySeedsService.getSeedFacts())
            }
        }
    }

}