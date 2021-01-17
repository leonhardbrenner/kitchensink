import kotlinx.serialization.Serializable

class JohnnySeedsDto {

    @Serializable
    data class DetailedSeedDto(
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
        //TODO - consider making links within here.
    }

    @Serializable
    data class CategoryDto(
            override val name: String,
            override val image: String,
            override val link: String
    ): JohnnySeeds.Category {
        companion object {
            val path = "/johnnySeeds/category"
        }
    }

    @Serializable
    data class BasicSeedDto(
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
    data class SeedFactsDto(
            override val name: String,
            override val facts: List<String>?,
            override val maturity: String?
    ): JohnnySeeds.SeedFacts {
        companion object {
            val path = "/johnnySeeds/seedFacts"
        }
    }

}
