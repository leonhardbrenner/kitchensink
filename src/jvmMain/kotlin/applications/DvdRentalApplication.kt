package applications

import com.google.inject.AbstractModule
import generated.model.DvdRentalDto
import generated.model.db.DvdRentalDb
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.inject.Inject

class DvdRentalService {
    val actors get() = DvdRentalDb.Actor.fetchAll()
    val addresses get() = DvdRentalDb.Address.fetchAll()
    val category get() = DvdRentalDb.Category.fetchAll()
    val city get() = DvdRentalDb.City.fetchAll()
    val country get() = DvdRentalDb.Country.fetchAll()
    val customer get() = DvdRentalDb.Customer.fetchAll()
    val film get() = DvdRentalDb.Film.fetchAll()
    val filmActor get() = DvdRentalDb.FilmActor.fetchAll()
    val filmCategory get() = DvdRentalDb.FilmCategory.fetchAll()
    val inventory get() = DvdRentalDb.Inventory.fetchAll()
    val language get() = DvdRentalDb.Language.fetchAll()
    val payment get() = DvdRentalDb.Payment.fetchAll()
    val rental get() = DvdRentalDb.Rental.fetchAll()
    val staff get() = DvdRentalDb.Staff.fetchAll()
    val store get() = DvdRentalDb.Store.fetchAll()
}

class DvdRentalApplication @Inject constructor(val dvdRentalService: DvdRentalService) {

    object Module : AbstractModule()

    fun routesFrom(routing: Routing) = routing.apply {

        route(DvdRentalDto.Actor.path) {
            get {
                call.respond(dvdRentalService.actors)
            }
        }
        route(DvdRentalDto.Address.path) {
            get {
                call.respond(dvdRentalService.addresses)
            }
        }
        route(DvdRentalDto.Category.path) {
            get {
                call.respond(dvdRentalService.category)
            }
        }
        route(DvdRentalDto.City.path) {
            get {
                call.respond(dvdRentalService.city)
            }
        }
        route(DvdRentalDto.Country.path) {
            get {
                call.respond(dvdRentalService.country)
            }
        }
        route(DvdRentalDto.Customer.path) {
            get {
                call.respond(dvdRentalService.customer)
            }
        }
        route(DvdRentalDto.Film.path) {
            get {
                call.respond(dvdRentalService.film)
            }
        }
        route(DvdRentalDto.FilmActor.path) {
            get {
                call.respond(dvdRentalService.filmActor)
            }
        }
        route(DvdRentalDto.FilmCategory.path) {
            get {
                call.respond(dvdRentalService.filmCategory)
            }
        }
        route(DvdRentalDto.Inventory.path) {
            get {
                call.respond(dvdRentalService.inventory)
            }
        }
        route(DvdRentalDto.Language.path) {
            get {
                call.respond(dvdRentalService.language)
            }
        }
        route(DvdRentalDto.Payment.path) {
            get {
                call.respond(dvdRentalService.payment)
            }
        }
        route(DvdRentalDto.Rental.path) {
            get {
                call.respond(dvdRentalService.rental)
            }
        }
        route(DvdRentalDto.Staff.path) {
            get {
                call.respond(dvdRentalService.staff)
            }
        }
        route(DvdRentalDto.Store.path) {
            get {
                call.respond(dvdRentalService.store)
            }
        }

    }

}