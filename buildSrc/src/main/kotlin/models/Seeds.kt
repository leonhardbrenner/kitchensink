package models

import schema.ManifestNew

interface Seeds {
    interface BasicSeed {
        val name: String
        val secondary_name: String
        val description: String?
        val image: String
        val link: String
    }

    interface SeedCategory {
        val name: String
        val image: String
        val link: String
    }

    interface DetailedSeed {
        val name: String
        val maturity: String?
        val secondary_name: String?
        val description: String?
        val image: String?
        val link: String?
    }

    interface SeedFacts {
        val name: String
        //TODO - This should be a list
        val facts: String?
        val maturity: String?
    }
}
val seeds = ManifestNew.Namespace(Seeds::class)
