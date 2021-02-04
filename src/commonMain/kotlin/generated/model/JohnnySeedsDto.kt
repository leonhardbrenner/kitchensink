package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface JohnnySeedsDto {
  @Serializable
  data class DetailedSeeds(
    override val name: String,
    override val maturity: String?,
    override val secondary_name: String?,
    override val description: String?,
    override val image: String?,
    override val link: String?
  ) : JohnnySeeds.DetailedSeeds {
    companion object {
      final val path: String = "/JohnnySeeds/DetailedSeeds"

      final val header: String = "name\tmaturity\tsecondary_name\tdescription\timage\tlink"


      fun create(source: JohnnySeeds.DetailedSeeds) = DetailedSeeds(source.name, source.maturity,
          source.secondary_name, source.description, source.image, source.link)}
  }

  @Serializable
  data class Category(
    override val name: String,
    override val image: String,
    override val link: String
  ) : JohnnySeeds.Category {
    companion object {
      final val path: String = "/JohnnySeeds/Category"

      final val header: String = "name\timage\tlink"


      fun create(source: JohnnySeeds.Category) = Category(source.name, source.image, source.link)}
  }

  @Serializable
  data class BasicSeed(
    override val name: String,
    override val secondary_name: String,
    override val description: String?,
    override val image: String,
    override val link: String
  ) : JohnnySeeds.BasicSeed {
    companion object {
      final val path: String = "/JohnnySeeds/BasicSeed"

      final val header: String = "name\tsecondary_name\tdescription\timage\tlink"


      fun create(source: JohnnySeeds.BasicSeed) = BasicSeed(source.name, source.secondary_name,
          source.description, source.image, source.link)}
  }

  @Serializable
  data class SeedFacts(
    override val name: String,
    override val facts: String?,
    override val maturity: String?
  ) : JohnnySeeds.SeedFacts {
    companion object {
      final val path: String = "/JohnnySeeds/SeedFacts"

      final val header: String = "name\tfacts\tmaturity"


      fun create(source: JohnnySeeds.SeedFacts) = SeedFacts(source.name, source.facts,
          source.maturity)}
  }
}
