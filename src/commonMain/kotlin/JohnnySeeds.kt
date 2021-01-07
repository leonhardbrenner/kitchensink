import kotlinx.serialization.Serializable

class JohnnySeeds {
    companion object {
        const val path = "/johnnySeeds"
    }

    @Serializable
    data class DetailedSeed(
        val name: String,
        val maturity: String?,
        val secondary_name: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    @Serializable
    data class Category(
        val name: String,
        val image: String,
        val link: String
    )

    @Serializable
    data class BasicSeed(
        val name: String,
        val secondary_name: String,
        val description: String?,
        val image: String,
        val link: String
    )

    data class SeedFacts(
        val name: String,
        val facts: List<String>?,
        val maturity: String?
    )
}
