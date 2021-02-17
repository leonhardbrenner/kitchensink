package models

//Test case for things like tables, csv, and all other 2D structures
interface Flat {
    class A(
        val boolean: Boolean,
        val int: Int,
        val long: Long,
        val double: Double,
        val string: String
    )
}
