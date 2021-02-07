package applications

import com.google.inject.AbstractModule
import generated.model.SeedsDto
import generated.model.db.SeedsDb
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.inject.Inject

class SeedsService {
    fun getDetailedSeeds() = SeedsDb.DetailedSeed.fetchAll().subList(0, 100)
    fun getCategories() = SeedsDb.SeedCategory.fetchAll().subList(0, 100)
    fun getBasicSeeds() = SeedsDb.BasicSeed.fetchAll().subList(0, 100)
    fun getSeedFacts() = SeedsDb.SeedFacts.fetchAll().let {
        it.subList(0, if (it.size < 100) it.size else 100)
    }
}

class SeedsApplication @Inject constructor(val seedsService: SeedsService) {

    object Module : AbstractModule()

    fun routesFrom(routing: Routing) = routing.apply {
        route(SeedsDto.DetailedSeed.path) {
            get {
                call.respond(seedsService.getDetailedSeeds())
            }
        }
        route(SeedsDto.SeedCategory.path) {
            get {
                call.respond(seedsService.getCategories())
            }
        }
        route(SeedsDto.BasicSeed.path) {
            get {
                call.respond(seedsService.getBasicSeeds())
            }
        }
        route(SeedsDto.SeedFacts.path) {
            get {
                call.respond(seedsService.getSeedFacts())
            }
        }
    }

}