package models

import schema.Manifest
import schema.Type //TODO Make this a method of Manifest

val manifest = Manifest("Manifest") {
    Element("JohnnySeeds") {
        Element("DetailedSeeds") {
            Element("name", type = String::class)
            Element("maturity", type = String::class, nullable = true)
            Element("secondary_name", type = String::class, nullable = true)
            Element("description", type = String::class, nullable = true)
            Element("image", type = String::class, nullable = true)
            Element("link", type = String::class, nullable = true)
        }

        Element("Category") {
            Element("name", type = String::class)
            Element("image", type = String::class)
            Element("link", type = String::class)
        }

        Element("BasicSeed") {
            Element("name", type = String::class)
            Element("secondary_name", type = String::class)
            Element("description", type = String::class, nullable = true)
            Element("image", type = String::class)
            Element("link", type = String::class)
        }

        Element("SeedFacts") {
            Element("name", type = String::class)
            Element("facts", type = String::class, nullable = true) //TODO - This should be a list
            Element("maturity", type = String::class, nullable = true)
        }
        //Element("DetailedSeedsWithCategoryAndFacts") {
        //    Element("name", Type(String::class))
        //    Element("maturity", Type(String::class, true))
        //    Element("secondary_name", Type(String::class, true))
        //    Element("description", Type(String::class, true))
        //    Element("image", Type(String::class, true))
        //    Element("link", Type(String::class, true))
        //    //Element("facts", Ref("SeedFacts"))
        //}

        //val x = Array<String>::class
    }
}
