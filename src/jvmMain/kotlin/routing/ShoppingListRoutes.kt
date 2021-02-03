package routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import applications.ShoppingListApplication
import models.ShoppingListItem

fun Routing.shopingListRoutes() = route(ShoppingListItem.path) {

    get {
        call.respond(ShoppingListApplication.shoppingListService.get())
    }

    post {
        val item = call.receive<ShoppingListItem>()
        ShoppingListApplication.shoppingListService.post(item)
        call.respond(HttpStatusCode.OK)
    }

    delete("/{id}") {
        val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
        ShoppingListApplication.shoppingListService.delete(id)
        call.respond(HttpStatusCode.OK)
    }
}