package models

import schemanew.Manifest

val johnnySeedsNew = Manifest.namespace("JohnnySeeds") {
    element("DetailedSeeds", type = "DetailedSeed")
    complexType("DetailedSeeds") {
        element("name", type = "builtin:string")
        element("maturity", type = "builtin:nullableString")
        element("secondary_name", type = "builtin:nullableString")
        element("description", type = "builtin:nullableString")
        element("image", type = "builtin:nullableString")
        element("link", type = "builtin:nullableString")
    }

    element("Category") {
        element("name", type = "builtin:string")
        element("image", type = "builtin:string")
        element("link", type = "builtin:string")
    }

    element("BasicSeed") {
        element("name", type = "builtin:string")
        element("secondary_name", type = "builtin:string")
        element("description", type = "builtin:nullableString")
        element("image", type = "builtin:string")
        element("link", type = "builtin:string")
    }

    element("SeedFacts") {
        element("name", type = "builtin:string")
        //TODO - This should be a list
        element("facts", type = "builtin:nullableString")
        element("maturity", type = "builtin:nullableString")
    }
}
