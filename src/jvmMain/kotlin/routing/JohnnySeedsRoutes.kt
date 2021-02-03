package routing

import generated.model.JohnnySeedsDto
import generated.model.db.JohnnySeedsDb
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.JohnnySeedsRoutes() {
    route(JohnnySeedsDto.DetailedSeeds.path) {
        get {
            call.respond(JohnnySeedsDb.DetailedSeeds.fetchAll().subList(0, 100))
        }
    }
    route(JohnnySeedsDto.Category.path) {
        get {
            call.respond(JohnnySeedsDb.Category.fetchAll().subList(0, 100))
        }
    }
    route(JohnnySeedsDto.BasicSeed.path) {
        get {
            call.respond(JohnnySeedsDb.BasicSeed.fetchAll().subList(0, 100))
        }
    }
    route(JohnnySeedsDto.SeedFacts.path) {
        get {
            call.respond(JohnnySeedsDb.SeedFacts.fetchAll().let { it ->
                it.subList(0, if (it.size < 100) it.size else 100)
            })
        }
    }
}
