package generated.model

import java.io.File
import kotlin.Int
import kotlin.Long
import kotlin.String

interface FlatCsvLoader {
  data class A(
    override val int: Int,
    override val long: Long,
    override val string: String
  ) : Flat.A {
    companion object {
      val header: String = "int\tlong\tstring"

      fun loadCsv(file: File) = model.loadCsv<A>(file, header)}
  }
}
