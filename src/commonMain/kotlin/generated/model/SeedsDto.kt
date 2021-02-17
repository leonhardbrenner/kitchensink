package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface SeedsDto {
  @Serializable
  data class BasicSeed(
    override val name: kotlin.String,
    override val secondary_name: kotlin.String,
    override val description: kotlin.String?,
    override val image: kotlin.String,
    override val link: kotlin.String
  ) : Seeds.BasicSeed {
    companion object {
      val path: String = "/Seeds/BasicSeed"

      fun create(source: Seeds.BasicSeed) = SeedsDto.BasicSeed(source.name, source.secondary_name,
          source.description, source.image, source.link)}
  }

  @Serializable
  data class DetailedSeed(
    override val name: kotlin.String,
    override val maturity: kotlin.String?,
    override val secondary_name: kotlin.String?,
    override val description: kotlin.String?,
    override val image: kotlin.String?,
    override val link: kotlin.String?
  ) : Seeds.DetailedSeed {
    companion object {
      val path: String = "/Seeds/DetailedSeed"

      fun create(source: Seeds.DetailedSeed) = SeedsDto.DetailedSeed(source.name, source.maturity,
          source.secondary_name, source.description, source.image, source.link)}
  }

  @Serializable
  data class SeedCategory(
    override val name: kotlin.String,
    override val image: kotlin.String,
    override val link: kotlin.String
  ) : Seeds.SeedCategory {
    companion object {
      val path: String = "/Seeds/SeedCategory"

      fun create(source: Seeds.SeedCategory) = SeedsDto.SeedCategory(source.name, source.image,
          source.link)}
  }

  @Serializable
  data class SeedFacts(
    override val name: kotlin.String,
    override val facts: kotlin.String?,
    override val maturity: kotlin.String?
  ) : Seeds.SeedFacts {
    companion object {
      val path: String = "/Seeds/SeedFacts"

      fun create(source: Seeds.SeedFacts) = SeedsDto.SeedFacts(source.name, source.facts,
          source.maturity)}
  }
}
