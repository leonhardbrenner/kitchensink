package generated.model

import java.io.File
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String

interface FlatCsvLoader {
  data class A(
    override val string: String,
    override val int: Int,
    override val double: Double,
    override val long: Long,
    override val boolean: Boolean
  ) : Flat.A {
    companion object {
      val header: String = "string\tint\tdouble\tlong\tboolean"

      fun loadCsv(file: File) = model.loadCsv<A>(file, header)}
  }
}
