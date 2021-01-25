import kotlinx.serialization.Serializable

interface JohnnySeedsDto {

    @Serializable
    data class DetailedSeed(
            override val name: String,
            override val maturity: String?,
            override val secondary_name: String?,
            override val description: String?,
            override val image: String?,
            override val link: String?
    ): JohnnySeeds.DetailedSeed {
        companion object {
            val path = "/johnnySeeds/detailedSeed"
        }
        fun create(source: JohnnySeeds.DetailedSeed) = DetailedSeed(
                source.name, source.maturity, source.secondary_name, source.description, source.image, source.link)
    }

    @Serializable
    data class Category(
            override val name: String,
            override val image: String,
            override val link: String
    ): JohnnySeeds.Category {
        companion object {
            val path = "/johnnySeeds/category"
        }
    }

    @Serializable
    data class BasicSeed(
            override val name: String,
            override val secondary_name: String,
            override val description: String?,
            override val image: String,
            override val link: String
    ): JohnnySeeds.BasicSeed {
        companion object {
            val path = "/johnnySeeds/basicSeed"
        }
    }

    @Serializable
    data class SeedFacts(
            override val name: String,
            override val facts: List<String>?,
            override val maturity: String?
    ): JohnnySeeds.SeedFacts {
        companion object {
            val path = "/johnnySeeds/seedFacts"
        }
    }

}
