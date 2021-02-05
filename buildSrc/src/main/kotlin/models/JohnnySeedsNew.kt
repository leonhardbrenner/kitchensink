package models

import schemanew.Manifest

val seeds = Manifest.namespace("JohnnySeeds") {
    element("DetailedSeeds", "DetailedSeed")
    complexType("DetailedSeeds") {
        element("name", "builtin:string")
        element("maturity", "builtin:nullableString")
        element("secondary_name", "builtin:nullableString")
        element("description", "builtin:nullableString")
        element("image", "builtin:nullableString")
        element("link", "builtin:nullableString")
    }
    element("Category") {
        element("name", "builtin:string")
        element("image", "builtin:string")
        element("link", "builtin:string")
    }

    element("BasicSeed") {
        element("name", "builtin:string")
        element("secondary_name", "builtin:string")
        element("description", "builtin:nullableString")
        element("image", "builtin:string")
        element("link", "builtin:string")
    }

    element("SeedFacts") {
        element("name", "builtin:string")
        //TODO - This should be a list
        element("facts", "builtin:nullableString")
        element("maturity", "builtin:nullableString")
    }
}