package generated.model

import kotlin.String

interface JohnnySeeds {
  interface DetailedSeeds {
    val name: String

    val maturity: String?

    val secondary_name: String?

    val description: String?

    val image: String?

    val link: String?
  }

  interface Category {
    val name: String

    val image: String

    val link: String
  }

  interface BasicSeed {
    val name: String

    val secondary_name: String

    val description: String?

    val image: String

    val link: String
  }

  interface SeedFacts {
    val name: String

    val facts: String?

    val maturity: String?
  }
}
