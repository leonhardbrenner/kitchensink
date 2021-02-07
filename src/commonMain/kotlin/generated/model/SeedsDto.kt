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
      final val path: String = "/Seeds/BasicSeed"

      final val header: String = "description\timage\tlink\tname\tsecondary_name"


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
      final val path: String = "/Seeds/DetailedSeed"

      final val header: String = "description\timage\tlink\tmaturity\tname\tsecondary_name"


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
      final val path: String = "/Seeds/SeedCategory"

      final val header: String = "image\tlink\tname"


      fun create(source: Seeds.SeedCategory) = SeedCategory(source.image, source.link, source.name)}
  }

  @Serializable
  data class SeedFacts(
    override val facts: String?,
    override val maturity: String?,
    override val name: String
  ) : Seeds.SeedFacts {
    companion object {
      final val path: String = "/Seeds/SeedFacts"

      final val header: String = "facts\tmaturity\tname"


      fun create(source: Seeds.SeedFacts) = SeedFacts(source.facts, source.maturity, source.name)}
  }
}
