package generated.model

import kotlin.String

interface Seeds {
  interface BasicSeed {
    val description: String?

    val image: String

    val link: String

    val name: String

    val secondary_name: String
  }

  interface DetailedSeed {
    val description: String?

    val image: String?

    val link: String?

    val maturity: String?

    val name: String

    val secondary_name: String?
  }

  interface SeedCategory {
    val image: String

    val link: String

    val name: String
  }

  interface SeedFacts {
    val facts: String?

    val maturity: String?

    val name: String
  }
}
