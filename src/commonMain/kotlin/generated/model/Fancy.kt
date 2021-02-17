package generated.model

import kotlin.Int

interface Fancy {
  interface A {
    val b: Fancy.A.B

    val nullableB: Fancy.A.B?

    val listOfB: Fancy.A.B

    val listOfNullableB: Fancy.A.B?

    val nullableListOfB: Fancy.A.B

    val nullableListOfNullableB: Fancy.A.B?

    interface B {
      val c: Fancy.C

      val nullableC: Fancy.C?

      val listOfC: Fancy.C

      val listOfNullableC: Fancy.C?

      val nullableListOfC: Fancy.C

      val nullableListOfNullableC: Fancy.C?
    }
  }

  interface C {
    val x: Int?
  }
}
