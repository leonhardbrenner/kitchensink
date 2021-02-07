package models

import schemanew.Manifest

val seeds = Manifest.namespace("Seeds") {
    complexType("BasicSeed") {
        element("name", "builtin:string")
        element("secondary_name", "builtin:string")
        element("description", "builtin:nullableString")
        element("image", "builtin:string")
        element("link", "builtin:string")
    }

    complexType("SeedCategory") {
        element("name", "builtin:string")
        element("image", "builtin:string")
        element("link", "builtin:string")
    }

    complexType("DetailedSeed") {
        element("name", "builtin:string")
        element("maturity", "builtin:nullableString")
        element("secondary_name", "builtin:nullableString")
        element("description", "builtin:nullableString")
        element("image", "builtin:nullableString")
        element("link", "builtin:nullableString")
    }

    complexType("SeedFacts") {
        element("name", "builtin:string")
        //TODO - This should be a list
        element("facts", "builtin:nullableString")
        element("maturity", "builtin:nullableString")
    }
}