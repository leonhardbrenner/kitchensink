package generated.model

import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface FancyDto {
  @Serializable
  data class A(
    override val b: Fancy.A.B,
    override val nullableB: Fancy.A.B?,
    override val listOfB: Fancy.A.B,
    override val listOfNullableB: Fancy.A.B?,
    override val nullableListOfB: Fancy.A.B,
    override val nullableListOfNullableB: Fancy.A.B?
  ) : Fancy.A {
    companion object {
      const val path: String = "/Fancy/A"

      fun create(source: Fancy.A) = FancyDto.A(source.b, source.nullableB, source.listOfB,
          source.listOfNullableB, source.nullableListOfB, source.nullableListOfNullableB)}

    @Serializable
    data class B(
      override val c: Fancy.C,
      override val nullableC: Fancy.C?,
      override val listOfC: Fancy.C,
      override val listOfNullableC: Fancy.C?,
      override val nullableListOfC: Fancy.C,
      override val nullableListOfNullableC: Fancy.C?
    ) : Fancy.A.B {
      companion object {
        const val path: String = "/Fancy/A/B"

        fun create(source: Fancy.A.B) = FancyDto.A.B(source.c, source.nullableC, source.listOfC,
            source.listOfNullableC, source.nullableListOfC, source.nullableListOfNullableC)}
    }
  }

  @Serializable
  data class C(
    override val x: Int?
  ) : Fancy.C {
    companion object {
      const val path: String = "/Fancy/C"

      fun create(source: Fancy.C) = FancyDto.C(source.x)}
  }
}
