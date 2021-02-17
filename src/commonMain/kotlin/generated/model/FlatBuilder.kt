package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String

interface FlatBuilder {
  class A(
    var boolean: Boolean?,
    var int: Int?,
    var long: Long?,
    var double: Double?,
    var string: String?
  ) {
    fun build(): Flat.A = FlatDto.A(
    boolean ?: throw IllegalArgumentException("boolean is not nullable"),
    int ?: throw IllegalArgumentException("int is not nullable"),
    long ?: throw IllegalArgumentException("long is not nullable"),
    double ?: throw IllegalArgumentException("double is not nullable"),
    string ?: throw IllegalArgumentException("string is not nullable")
    )}
}
