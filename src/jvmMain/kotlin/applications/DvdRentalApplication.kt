package applications

import generated.model.DvdRentalDto
import generated.model.db.DvdRentalDb
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

object DvdRentalApplication {

    fun routesFrom(routing: Routing) = routing.apply {
        route(DvdRentalDto.actor.path) {
            get {
                call.respond(DvdRentalDb.actor.fetchAll().subList(0, 100))
            }
        }
        route(DvdRentalDto.address.path) {
            get {
                call.respond(DvdRentalDb.address.fetchAll().subList(0, 100))
            }
        }
    }

}