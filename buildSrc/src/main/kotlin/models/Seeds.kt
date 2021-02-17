package models

import schema.Manifest

interface Seeds {
    class BasicSeed(
        val name: String,
        val secondary_name: String,
        val description: String?,
        val image: String,
        val link: String
    )

    class SeedCategory(
        val name: String,
        val image: String,
        val link: String
    )

    class DetailedSeed(
        val name: String,
        val maturity: String?,
        val secondary_name: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    class SeedFacts(
        val name: String,
        //TODO - This should be a list
        val facts: String?,
        val maturity: String?
    )
}
