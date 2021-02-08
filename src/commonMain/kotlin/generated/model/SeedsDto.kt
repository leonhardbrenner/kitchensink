package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface SeedsDto {
  @Serializable
  data class BasicSeed(
    override val description: String?,
    override val image: String,
    override val link: String,
    override val name: String,
    override val secondary_name: String
  ) : Seeds.BasicSeed {
    companion object {
      val path: String = "/Seeds/BasicSeed"

      fun create(source: Seeds.BasicSeed) = BasicSeed(source.description, source.image, source.link,
          source.name, source.secondary_name)}
  }

  @Serializable
  data class DetailedSeed(
    override val description: String?,
    override val image: String?,
    override val link: String?,
    override val maturity: String?,
    override val name: String,
    override val secondary_name: String?
  ) : Seeds.DetailedSeed {
    companion object {
      val path: String = "/Seeds/DetailedSeed"

      fun create(source: Seeds.DetailedSeed) = DetailedSeed(source.description, source.image,
          source.link, source.maturity, source.name, source.secondary_name)}
  }

  @Serializable
  data class SeedCategory(
    override val image: String,
    override val link: String,
    override val name: String
  ) : Seeds.SeedCategory {
    companion object {
      val path: String = "/Seeds/SeedCategory"

      fun create(source: Seeds.SeedCategory) = SeedCategory(source.image, source.link, source.name)}
  }

  @Serializable
  data class SeedFacts(
    override val facts: String?,
    override val maturity: String?,
    override val name: String
  ) : Seeds.SeedFacts {
    companion object {
      val path: String = "/Seeds/SeedFacts"

      fun create(source: Seeds.SeedFacts) = SeedFacts(source.facts, source.maturity, source.name)}
  }
}
