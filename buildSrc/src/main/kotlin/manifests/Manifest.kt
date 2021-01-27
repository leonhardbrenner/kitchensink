val johnnySeeds = Manifest("JohnnySeeds") {
    Element("DetailedSeeds") {
        Element("name", Type(String::class))
        Element("maturity", Type(String::class))
        Element("secondary_name", Type(String::class, true))
        Element("description", Type(String::class, true))
        Element("image", Type(String::class, true))
        Element("link", Type(String::class, true))
    }
}
