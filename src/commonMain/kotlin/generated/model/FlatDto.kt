package generated.model

import kotlin.String
import kotlinx.serialization.Serializable

interface FlatDto {
  @Serializable
  data class A(
    override val int: kotlin.Int,
    override val long: kotlin.Long,
    override val string: kotlin.String
  ) : Flat.A {
    companion object {
      val path: String = "/Flat/A"

      fun create(source: Flat.A) = FlatDto.A(source.int, source.long, source.string)}
  }
}
