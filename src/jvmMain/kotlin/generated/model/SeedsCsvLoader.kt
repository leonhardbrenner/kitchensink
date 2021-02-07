package generated.model

import kotlin.String

interface SeedsCsvLoader {
  data class BasicSeed(
    override val description: String?,
    override val image: String,
    override val link: String,
    override val name: String,
    override val secondary_name: String
  ) : Seeds.BasicSeed {
    companion object {
      final val path: String = "/JohnnySeeds/BasicSeed"

      final val header: String = "description\timage\tlink\tname\tsecondary_name"


      fun create(source: Seeds.BasicSeed) = BasicSeed(source.description, source.image,
          source.link, source.name, source.secondary_name)
      fun loadCsv(pathname: String) = model.loadCsv<BasicSeed>(pathname, header)}
  }

  data class Category(
    override val image: String,
    override val link: String,
    override val name: String
  ) : Seeds.Category {
    companion object {
      final val path: String = "/JohnnySeeds/Category"

      final val header: String = "image\tlink\tname"


      fun create(source: Seeds.Category) = Category(source.image, source.link, source.name)
      fun loadCsv(pathname: String) = model.loadCsv<Category>(pathname, header)}
  }

  data class DetailedSeeds(
    override val description: String?,
    override val image: String?,
    override val link: String?,
    override val maturity: String?,
    override val name: String,
    override val secondary_name: String?
  ) : Seeds.DetailedSeeds {
    companion object {
      final val path: String = "/JohnnySeeds/DetailedSeeds"

      final val header: String = "description\timage\tlink\tmaturity\tname\tsecondary_name"


      fun create(source: Seeds.DetailedSeeds) = DetailedSeeds(source.description,
          source.image, source.link, source.maturity, source.name, source.secondary_name)
      fun loadCsv(pathname: String) = model.loadCsv<DetailedSeeds>(pathname, header)}
  }

  data class SeedFacts(
    override val facts: String?,
    override val maturity: String?,
    override val name: String
  ) : Seeds.SeedFacts {
    companion object {
      final val path: String = "/JohnnySeeds/SeedFacts"

      final val header: String = "facts\tmaturity\tname"


      fun create(source: Seeds.SeedFacts) = SeedFacts(source.facts, source.maturity,
          source.name)
      fun loadCsv(pathname: String) = model.loadCsv<SeedFacts>(pathname, header)}
  }
}
