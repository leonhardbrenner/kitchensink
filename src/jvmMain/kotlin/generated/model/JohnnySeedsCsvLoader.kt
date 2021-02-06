package generated.model

import kotlin.String

interface JohnnySeedsCsvLoader {
  data class DetailedSeeds(
    override val description: String?,
    override val image: String?,
    override val link: String?,
    override val maturity: String?,
    override val name: String,
    override val secondary_name: String?
  ) : JohnnySeeds.DetailedSeeds {
    companion object {
      final val path: String = "/JohnnySeeds/DetailedSeeds"

      final val header: String = "description\timage\tlink\tmaturity\tname\tsecondary_name"


      fun create(source: JohnnySeeds.DetailedSeeds) = DetailedSeeds(source.name, source.maturity,
          source.secondary_name, source.description, source.image, source.link)
      fun loadCsv(pathname: String) = model.loadCsv<DetailedSeeds>(pathname, header)}
  }

  data class Category(
    override val image: String,
    override val link: String,
    override val name: String
  ) : JohnnySeeds.Category {
    companion object {
      final val path: String = "/JohnnySeeds/Category"

      final val header: String = "image\tlink\tname"


      fun create(source: JohnnySeeds.Category) = Category(source.name, source.image, source.link)
      fun loadCsv(pathname: String) = model.loadCsv<Category>(pathname, header)}
  }

  data class BasicSeed(
    override val description: String?,
    override val image: String,
    override val link: String,
    override val name: String,
    override val secondary_name: String
  ) : JohnnySeeds.BasicSeed {
    companion object {
      final val path: String = "/JohnnySeeds/BasicSeed"

      final val header: String = "description\timage\tlink\tname\tsecondary_name"


      fun create(source: JohnnySeeds.BasicSeed) = BasicSeed(source.name, source.secondary_name,
          source.description, source.image, source.link)
      fun loadCsv(pathname: String) = model.loadCsv<BasicSeed>(pathname, header)}
  }

  data class SeedFacts(
    override val facts: String?,
    override val maturity: String?,
    override val name: String
  ) : JohnnySeeds.SeedFacts {
    companion object {
      final val path: String = "/JohnnySeeds/SeedFacts"

      final val header: String = "facts\tmaturity\tname"


      fun create(source: JohnnySeeds.SeedFacts) = SeedFacts(source.name, source.facts,
          source.maturity)
      fun loadCsv(pathname: String) = model.loadCsv<SeedFacts>(pathname, header)}
  }
}
