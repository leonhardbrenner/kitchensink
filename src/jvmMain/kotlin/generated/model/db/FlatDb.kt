package generated.model.db

import generated.model.Flat
import generated.model.FlatDto
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
    fun create(source: ResultRow) = FlatDto.A(source[Table.int], source[Table.long],
        source[Table.string])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("A") {
      val int: Column<Int> = integer("int")

      val long: Column<Long> = long("long")

      val string: Column<String> = text("string")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Flat.A {
      override var int: Int by Table.int

      override var long: Long by Table.long

      override var string: String by Table.string

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: Flat.A) {
          Entity.new {
            int = source.int
            long = source.long
            string = source.string
          }
        }
      }
    }
  }
}
