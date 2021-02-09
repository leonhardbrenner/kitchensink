package generated.model

import kotlin.String

interface Seeds {
  interface BasicSeed {
    val name: String

    val secondary_name: String

    val description: String?

    val image: String

    val link: String
  }

  interface DetailedSeed {
    val name: String

    val maturity: String?

    val secondary_name: String?

    val description: String?

    val image: String?

    val link: String?
  }

  interface SeedCategory {
    val name: String

    val image: String

    val link: String
  }

  interface SeedFacts {
    val name: String

    val facts: String?

    val maturity: String?
  }
}
