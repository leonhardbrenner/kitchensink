package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface SeedsDto {
  @Serializable
  data class BasicSeed(
    override val name: String,
    override val secondary_name: String,
    override val description: String?,
    override val image: String,
    override val link: String
  ) : Seeds.BasicSeed {
    companion object {
      val path: String = "/Seeds/BasicSeed"

      fun create(source: Seeds.BasicSeed) = BasicSeed(source.name, source.secondary_name,
          source.description, source.image, source.link)}
  }

  @Serializable
  data class DetailedSeed(
    override val name: String,
    override val maturity: String?,
    override val secondary_name: String?,
    override val description: String?,
    override val image: String?,
    override val link: String?
  ) : Seeds.DetailedSeed {
    companion object {
      val path: String = "/Seeds/DetailedSeed"

      fun create(source: Seeds.DetailedSeed) = DetailedSeed(source.name, source.maturity,
          source.secondary_name, source.description, source.image, source.link)}
  }

  @Serializable
  data class SeedCategory(
    override val name: String,
    override val image: String,
    override val link: String
  ) : Seeds.SeedCategory {
    companion object {
      val path: String = "/Seeds/SeedCategory"

      fun create(source: Seeds.SeedCategory) = SeedCategory(source.name, source.image, source.link)}
  }

  @Serializable
  data class SeedFacts(
    override val name: String,
    override val facts: String?,
    override val maturity: String?
  ) : Seeds.SeedFacts {
    companion object {
      val path: String = "/Seeds/SeedFacts"

      fun create(source: Seeds.SeedFacts) = SeedFacts(source.name, source.facts, source.maturity)}
  }
}
