package generated.model

import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface FancyDto {
  @Serializable
  data class A(
    override val b: Fancy.A.B,
    override val listOfB: Fancy.A.B,
    override val listOfNullableB: Fancy.A.B?,
    override val nullableB: Fancy.A.B?,
    override val nullableListOfB: Fancy.A.B,
    override val nullableListOfNullableB: Fancy.A.B?
  ) : Fancy.A {
    companion object {
      val path: String = "A"

      fun create(source: A) = A(source.b, source.listOfB, source.listOfNullableB, source.nullableB,
          source.nullableListOfB, source.nullableListOfNullableB)
      @Serializable
      data class B(
        override val c: Fancy.C,
        override val listOfC: Fancy.C,
        override val listOfNullableC: Fancy.C?,
        override val nullableC: Fancy.C?,
        override val nullableListOfC: Fancy.C,
        override val nullableListOfNullableC: Fancy.C?
      ) : Fancy.A.B {
        companion object {
          val path: String = "Fancy/A/B"

          fun create(source: Fancy.A.B) = B(source.c, source.listOfC,
              source.listOfNullableC, source.nullableC, source.nullableListOfC,
              source.nullableListOfNullableC)}
      }
    }
  }

  @Serializable
  data class C(
    override val x: Int?
  ) : Fancy.C {
    companion object {
      val path: String = "C"

      fun create(source: C) = C(source.x)}
  }
}
