package models

import generated.model.DvdRentalDto

import kotlin.Int
import kotlin.String

interface DvdRentalBuilder {

    data class Actor(
        var actorId: Int? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var lastUpdate: String? = null
    ) {
        fun build() = DvdRentalDto.Actor(
            actorId ?: throw IllegalArgumentException("actorId is not nullable."),
            firstName ?: throw IllegalArgumentException("firstName is not nullable."),
            lastName ?: throw IllegalArgumentException("lastName is not nullable."),
            lastUpdate ?: throw IllegalArgumentException("lastUpdate is not nullable.")
        )
    }
}