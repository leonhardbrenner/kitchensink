package models

//TODO - replace this! Look at DvdRentalNew which has the syntax I am moving towards and this will make more sense.

import schema.Element

val johnnySeeds = Element.
model("Manifest") {

    x("JohnnySeeds") {

        x("DetailedSeeds") {
            x("name", String::class)
            x("maturity", String::class, nullable = true)
            x("secondary_name", String::class, nullable = true)
            x("description", String::class, nullable = true)
            x("image", String::class, nullable = true)
            x("link", String::class, nullable = true)
        }

        x("Category") {
            x("name", String::class)
            x("image", String::class)
            x("link", String::class)
        }

        x("BasicSeed") {
            x("name", String::class)
            x("secondary_name", String::class)
            x("description", String::class, nullable = true)
            x("image", String::class)
            x("link", String::class)
        }

        x("SeedFacts") {
            x("name", String::class)
            //TODO - This should be a list
            x("facts", String::class, nullable = true)
            x("maturity", String::class, nullable = true)
        }
    }
    x("")
}.namespaces.first()
