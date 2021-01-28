package models

import schema.Manifest
import schema.Type //TODO Make this a method of Manifest

val johnnySeeds = Manifest("JohnnySeeds") {

    Element("DetailedSeeds") {
        Element("name", Type(String::class))
        Element("maturity", Type(String::class, true))
        Element("secondary_name", Type(String::class, true))
        Element("description", Type(String::class, true))
        Element("image", Type(String::class, true))
        Element("link", Type(String::class, true))
    }

    Element("Category") {
        Element("name", Type(String::class))
        Element("image", Type(String::class))
        Element("link", Type(String::class))
    }

    Element("BasicSeed") {
        Element("name", Type(String::class))
        Element("secondary_name", Type(String::class))
        Element("description", Type(String::class, true))
        Element("image", Type(String::class))
        Element("link", Type(String::class))
    }

    Element("SeedFacts") {
        Element("name", Type(String::class))
        Element("facts", Type(String::class, true)) //TODO - This should be a list
        Element("maturity", Type(String::class, true))
    }
    val x = Array<String>::class
}
