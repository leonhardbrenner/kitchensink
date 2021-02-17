package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String

interface FlatBuilder {
  class A(
    var string: String?,
    var int: Int?,
    var double: Double?,
    var long: Long?,
    var boolean: Boolean?
  ) {
    fun build(): Flat.A = FlatDto.A(
    string ?: throw IllegalArgumentException("string is not nullable"),
    int ?: throw IllegalArgumentException("int is not nullable"),
    double ?: throw IllegalArgumentException("double is not nullable"),
    long ?: throw IllegalArgumentException("long is not nullable"),
    boolean ?: throw IllegalArgumentException("boolean is not nullable")
    )}
}
