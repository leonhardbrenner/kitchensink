package models

//Test case for things like json
interface Fancy {
    val a: A
    val nullableA: A?
    val listOfA: List<A>
    val listOfNullableA: List<A?>
    val nullableListOfA: List<A>?
    val nullableListOfNullableA: List<A?>?
    class A(
        val b: B,
        val nullableB: B?,
        val listOfB: List<B>,
        val listOfNullableB: List<B?>,
        val nullableListOfB: List<B>?,
        val nullableListOfNullableB: List<B?>?) {
        class B(
            val c: C,
            val nullableC: C?,
            val listOfC: List<C>,
            val listOfNullableC: List<C?>,
            val nullableListOfC: List<C>?,
            val nullableListOfNullableC: List<C?>?
        )
    }
    class C(
        val x: Int?
    )
}
