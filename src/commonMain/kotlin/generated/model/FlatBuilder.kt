package generated.model

import kotlin.Int
import kotlin.Long
import kotlin.String

interface FlatBuilder {
  class A(
    var int: Int?,
    var long: Long?,
    var string: String?
  ) {
    fun build(): Flat.A = FlatDto.A(
    int ?: throw IllegalArgumentException("int is not nullable"),
    long ?: throw IllegalArgumentException("long is not nullable"),
    string ?: throw IllegalArgumentException("string is not nullable")
    )}
}
