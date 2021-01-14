import kotlinx.serialization.Serializable

class JohnnySeeds {

    @Serializable
    data class DetailedSeed(
            val name: String,
            val maturity: String?,
            val secondary_name: String?,
            val description: String?,
            val image: String?,
            val link: String?
    ) {
        companion object {
            val path = "/johnnySeeds/detailedSeed"
        }
        //TODO - consider making links within here.
    }

    @Serializable
    data class Category(
            val name: String,
            val image: String,
            val link: String
    ) {
        companion object {
            val path = "/johnnySeeds/category"
        }
        //TODO - consider making links within here.
    }


    @Serializable
    data class BasicSeed(
            val name: String,
            val secondary_name: String,
            val description: String?,
            val image: String,
            val link: String
    ) {
        companion object {
            val path = "/johnnySeeds/basicSeed"
        }
        //TODO - consider making links within here.
    }


    @Serializable
    data class SeedFacts(
            val name: String,
            val facts: List<String>?,
            val maturity: String?
    ) {
        companion object {
            val path = "/johnnySeeds/seedFacts"
        }
        //TODO - consider making links within here.
    }

}
