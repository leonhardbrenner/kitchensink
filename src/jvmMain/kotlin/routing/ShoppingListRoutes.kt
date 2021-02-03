package routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import applications.ShoppingListApplication
import applications.ShoppingListService
import models.ShoppingListItem

fun Routing.shoppingListRoutes(shoppingListService: ShoppingListService) = route(ShoppingListItem.path) {

    get {
        call.respond(shoppingListService.get())
    }

    post {
        val item = call.receive<ShoppingListItem>()
        shoppingListService.post(item)
        call.respond(HttpStatusCode.OK)
    }

    delete("/{id}") {
        val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
        shoppingListService.delete(id)
        call.respond(HttpStatusCode.OK)
    }
}