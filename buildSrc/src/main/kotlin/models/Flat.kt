package models

//Test case for things like tables, csv, and all other 2D structures
interface Flat {
    class A(
        val string: String,
        val int: Int,
        val double: Double,
        val long: Long,
        val boolean: Boolean
    )
}
