package generated.model

import java.io.File
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String

interface FlatCsvLoader {
  data class A(
    override val boolean: Boolean,
    override val int: Int,
    override val long: Long,
    override val double: Double,
    override val string: String
  ) : Flat.A {
    companion object {
      val header: String = "boolean\tint\tlong\tdouble\tstring"

      fun loadCsv(file: File) = model.loadCsv<A>(file, header)}
  }
}
