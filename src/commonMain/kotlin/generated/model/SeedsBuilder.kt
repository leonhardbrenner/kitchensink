package generated.model

import kotlin.String

interface SeedsBuilder {
  class BasicSeed(
    var name: String?,
    var secondary_name: String?,
    var description: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.BasicSeed = SeedsDto.BasicSeed(
    name ?: throw IllegalArgumentException("name is not nullable"),
    secondary_name ?: throw IllegalArgumentException("secondary_name is not nullable"),
    description,
    image ?: throw IllegalArgumentException("image is not nullable"),
    link ?: throw IllegalArgumentException("link is not nullable")
    )}

  class DetailedSeed(
    var name: String?,
    var maturity: String?,
    var secondary_name: String?,
    var description: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.DetailedSeed = SeedsDto.DetailedSeed(
    name ?: throw IllegalArgumentException("name is not nullable"),
    maturity,
    secondary_name,
    description,
    image,
    link
    )}

  class SeedCategory(
    var name: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.SeedCategory = SeedsDto.SeedCategory(
    name ?: throw IllegalArgumentException("name is not nullable"),
    image ?: throw IllegalArgumentException("image is not nullable"),
    link ?: throw IllegalArgumentException("link is not nullable")
    )}

  class SeedFacts(
    var name: String?,
    var facts: String?,
    var maturity: String?
  ) {
    fun build(): Seeds.SeedFacts = SeedsDto.SeedFacts(
    name ?: throw IllegalArgumentException("name is not nullable"),
    facts,
    maturity
    )}
}
