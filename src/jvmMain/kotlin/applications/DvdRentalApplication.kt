package applications

import com.google.inject.AbstractModule
import generated.model.DvdRentalDto
import generated.model.db.DvdRentalDb
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.inject.Inject

class DvdRentalService {
    val actors get() = DvdRentalDb.actor.fetchAll()
    val addresses get() = DvdRentalDb.address.fetchAll()
    val category get() = DvdRentalDb.category.fetchAll()
    val city get() = DvdRentalDb.city.fetchAll()
    val country get() = DvdRentalDb.country.fetchAll()
    val customer get() = DvdRentalDb.customer.fetchAll()
    val film get() = DvdRentalDb.film.fetchAll()
    val film_actor get() = DvdRentalDb.film_actor.fetchAll()
    val film_category get() = DvdRentalDb.film_category.fetchAll()
    val inventory get() = DvdRentalDb.inventory.fetchAll()
    val language get() = DvdRentalDb.language.fetchAll()
    val payment get() = DvdRentalDb.payment.fetchAll()
    val rental get() = DvdRentalDb.rental.fetchAll()
    val staff get() = DvdRentalDb.staff.fetchAll()
    val store get() = DvdRentalDb.store.fetchAll()
}

class DvdRentalApplication @Inject constructor(val dvdRentalService: DvdRentalService) {

    object Module : AbstractModule()

    fun routesFrom(routing: Routing) = routing.apply {

        route(DvdRentalDto.actor.path) {
            get {
                call.respond(dvdRentalService.actors)
            }
        }

        route(DvdRentalDto.address.path) {
            get {
                call.respond(dvdRentalService.addresses)
            }
        }
        route(DvdRentalDto.category.path) {
            get {
                call.respond(dvdRentalService.category)
            }
        }
        route(DvdRentalDto.city.path) {
            get {
                call.respond(dvdRentalService.city)
            }
        }
        route(DvdRentalDto.country.path) {
            get {
                call.respond(dvdRentalService.country)
            }
        }
        route(DvdRentalDto.customer.path) {
            get {
                call.respond(dvdRentalService.customer)
            }
        }
        route(DvdRentalDto.film.path) {
            get {
                call.respond(dvdRentalService.film)
            }
        }
        route(DvdRentalDto.film_actor.path) {
            get {
                call.respond(dvdRentalService.film_actor)
            }
        }
        route(DvdRentalDto.film_category.path) {
            get {
                call.respond(dvdRentalService.film_category)
            }
        }
        route(DvdRentalDto.inventory.path) {
            get {
                call.respond(dvdRentalService.inventory)
            }
        }
        route(DvdRentalDto.language.path) {
            get {
                call.respond(dvdRentalService.language)
            }
        }
        route(DvdRentalDto.payment.path) {
            get {
                call.respond(dvdRentalService.payment)
            }
        }
        route(DvdRentalDto.rental.path) {
            get {
                call.respond(dvdRentalService.rental)
            }
        }
        route(DvdRentalDto.staff.path) {
            get {
                call.respond(dvdRentalService.staff)
            }
        }
        route(DvdRentalDto.store.path) {
            get {
                call.respond(dvdRentalService.store)
            }
        }

    }

}