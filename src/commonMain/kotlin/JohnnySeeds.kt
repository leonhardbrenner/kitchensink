import kotlinx.serialization.Serializable

interface JohnnySeeds {

    interface DetailedSeed {
        val name: String
        val maturity: String?
        val secondary_name: String?
        val description: String?
        val image: String?
        val link: String?

        companion object {
            val path = "/johnnySeeds/detailedSeed"
            fun create(source: DetailedSeed) = Dto(
                source.name, source.maturity, source.secondary_name, source.description, source.image, source.link
            )
        }

        @Serializable
        data class Dto(
            override val name: String,
            override val maturity: String?,
            override val secondary_name: String?,
            override val description: String?,
            override val image: String?,
            override val link: String?
        ) : DetailedSeed
    }


    interface Category {
        val name: String
        val image: String
        val link: String

        companion object {
            val path = "/johnnySeeds/category"
            fun create(source: Category) = Dto(source.name, source.image, source.link)
        }

        @Serializable
        data class Dto(
            override val name: String,
            override val image: String,
            override val link: String
        ) : Category
    }

    interface BasicSeed {
        val name: String
        val secondary_name: String
        val description: String?
        val image: String
        val link: String

        companion object {
            val path = "/johnnySeeds/basicSeed"
            fun create(source: DetailedSeed) = Dto(
                source.name, source.secondary_name!!, source.description, source.image!!, source.link!!
            )
        }

        @Serializable
        data class Dto(
            override val name: String,
            override val secondary_name: String,
            override val description: String?,
            override val image: String,
            override val link: String
        ) : BasicSeed
    }

    interface SeedFacts {
        val name: String
        val facts: List<String>?
        val maturity: String?

        companion object {
            val path = "/johnnySeeds/seedFacts"
        }

        @Serializable
        data class Dto(
            override val name: String,
            override val facts: List<String>?,
            override val maturity: String?
        ) : SeedFacts

    }
}
    //interface X {
    //    val a: Int
    //    val z: Z
    //    val y2: Y?
    //    val z2: Z?
    //    val y3: List<Y?>?
    //    val z3: List<Z?>?
    //    interface Y {
    //        val b: String
    //        val z22: List<Z?>?
    //    }
    //}
    //}
    //interface Z {
    //    val c: Double
    //    val d: List<String>
    //}
