package generated.model

import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

interface FlatDto {
  @Serializable
  data class A(
    override val boolean: Boolean,
    override val int: Int,
    override val long: Long,
    override val double: Double,
    override val string: String
  ) : Flat.A {
    companion object {
      const val path: String = "/Flat/A"

      fun create(source: Flat.A) = FlatDto.A(source.boolean, source.int, source.long, source.double,
          source.string)}
  }
}
