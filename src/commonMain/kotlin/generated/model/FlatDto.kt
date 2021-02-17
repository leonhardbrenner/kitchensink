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
    override val string: String,
    override val int: Int,
    override val double: Double,
    override val long: Long,
    override val boolean: Boolean
  ) : Flat.A {
    companion object {
      const val path: String = "/Flat/A"

      fun create(source: Flat.A) = FlatDto.A(source.string, source.int, source.double, source.long,
          source.boolean)}
  }
}
