import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import model.db.JohnnySeedsDb

//https://www.thebookofjoel.com/kotlin-ktor-exposed-postgres
fun main(args: Array<String>) {
    //an example connection to H2 DB
    //Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    DatabaseFactory.init()

    transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        //'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
        println("Seeds:")
        JohnnySeedsDb.DetailedSeed.Entity.all().forEach { seed ->
            println("\t${seed.name} - ${seed.maturity}")
        }
    }

}
