package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface FlatDto {
  @Serializable
  data class A(
    override val string: kotlin.String,
    override val int: kotlin.Int,
    override val double: kotlin.Double,
    override val long: kotlin.Long,
    override val boolean: kotlin.Boolean
  ) : Flat.A {
    companion object {
      const val path: String = "/Flat/A"

      fun create(source: Flat.A) = FlatDto.A(source.string, source.int, source.double, source.long,
          source.boolean)}
  }
}
