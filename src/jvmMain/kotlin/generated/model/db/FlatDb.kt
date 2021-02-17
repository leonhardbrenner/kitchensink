package generated.model.db

import generated.model.Flat
import generated.model.FlatDto
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object FlatDb {
  object A {
    fun create(source: ResultRow) = FlatDto.A(source[Table.string], source[Table.int],
        source[Table.double], source[Table.long], source[Table.boolean])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("A") {
      val string: Column<String> = text("string")

      val int: Column<Int> = integer("int")

      val double: Column<Double> = double("double")

      val long: Column<Long> = long("long")

      val boolean: Column<Boolean> = bool("boolean")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Flat.A {
      override var string: String by Table.string

      override var int: Int by Table.int

      override var double: Double by Table.double

      override var long: Long by Table.long

      override var boolean: Boolean by Table.boolean

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: Flat.A) {
          Entity.new {
            string = source.string
            int = source.int
            double = source.double
            long = source.long
            boolean = source.boolean
          }
        }
      }
    }
  }
}
