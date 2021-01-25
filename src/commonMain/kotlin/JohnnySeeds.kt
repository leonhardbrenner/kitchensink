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
        }
    }

    interface Category {
        val name: String
        val image: String
        val link: String
        companion object {
            val path = "/johnnySeeds/category"
        }
    }

    interface BasicSeed {
        val name: String
        val secondary_name: String
        val description: String?
        val image: String
        val link: String
        companion object {
            val path = "/johnnySeeds/basicSeed"
        }
    }

    interface SeedFacts {
        val name: String
        val facts: List<String>?
        val maturity: String?
        companion object {
            val path = "/johnnySeeds/seedFacts"
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
}
interface Z {
    val c: Double
    val d: List<String>
}
