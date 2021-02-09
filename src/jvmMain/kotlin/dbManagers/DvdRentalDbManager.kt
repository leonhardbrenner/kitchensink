package dbManagers

import generated.model.DvdRentalCsvLoader
import generated.model.db.DvdRentalDb
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

//Look across at dvd rentals the copy * from should be handled as a dsl off which loads
//the values in the order they appear in the type and inserts them just as we did above but this
//we are loading from a DSL. This seems like a good time to clean up code above. Also don't
//use x everywhere. Play tribute to XmlSchema and go SimpleType and ComplexType. :+2
object DvdRentalDBManager {

    fun drop() = transaction {
        SchemaUtils.drop(DvdRentalDb.Actor.Table)
        SchemaUtils.drop(DvdRentalDb.Address.Table)
        SchemaUtils.drop(DvdRentalDb.Category.Table)
        SchemaUtils.drop(DvdRentalDb.City.Table)
        SchemaUtils.drop(DvdRentalDb.Country.Table)
        SchemaUtils.drop(DvdRentalDb.Customer.Table)
        SchemaUtils.drop(DvdRentalDb.Film.Table)
        SchemaUtils.drop(DvdRentalDb.FilmActor.Table)
        SchemaUtils.drop(DvdRentalDb.FilmCategory.Table)
        SchemaUtils.drop(DvdRentalDb.Inventory.Table)
        SchemaUtils.drop(DvdRentalDb.Language.Table)
        SchemaUtils.drop(DvdRentalDb.Payment.Table)
        SchemaUtils.drop(DvdRentalDb.Rental.Table)
        SchemaUtils.drop(DvdRentalDb.Staff.Table)
        SchemaUtils.drop(DvdRentalDb.Store.Table)
    }

    fun create() = transaction {
        SchemaUtils.create(DvdRentalDb.Actor.Table)
        SchemaUtils.create(DvdRentalDb.Address.Table)
        SchemaUtils.create(DvdRentalDb.Category.Table)
        SchemaUtils.create(DvdRentalDb.City.Table)
        SchemaUtils.create(DvdRentalDb.Country.Table)
        SchemaUtils.create(DvdRentalDb.Customer.Table)
        SchemaUtils.create(DvdRentalDb.Film.Table)
        SchemaUtils.create(DvdRentalDb.FilmActor.Table)
        SchemaUtils.create(DvdRentalDb.FilmCategory.Table)
        SchemaUtils.create(DvdRentalDb.Inventory.Table)
        SchemaUtils.create(DvdRentalDb.Language.Table)
        SchemaUtils.create(DvdRentalDb.Payment.Table)
        SchemaUtils.create(DvdRentalDb.Rental.Table)
        SchemaUtils.create(DvdRentalDb.Staff.Table)
        SchemaUtils.create(DvdRentalDb.Store.Table)
    }

    fun populate() = transaction {
        DvdRentalCsvLoader.Actor.loadCsv(resource("dvdrental/3057.dat")).forEach { source ->
            DvdRentalDb.Actor.Entity.insert(source)
            println("Actor ${source.firstName} ${source.lastName}")
        }
        //XXX
        DvdRentalCsvLoader.Address.loadCsv(resource("dvdrental/3065.dat")).forEach { source ->
            DvdRentalDb.Address.Entity.insert(source)
            println("Address ${source.address} ${source.phone}")
        }
        DvdRentalCsvLoader.Category.loadCsv(resource("dvdrental/3059.dat")).forEach { source ->
            DvdRentalDb.Category.Entity.insert(source)
            println("Category ${source.name}")
        }
        DvdRentalCsvLoader.City.loadCsv(resource("dvdrental/3067.dat")).forEach { source ->
            DvdRentalDb.City.Entity.insert(source)
            println("City ${source.city}")
        }
        DvdRentalCsvLoader.Country.loadCsv(resource("dvdrental/3069.dat")).forEach { source ->
            DvdRentalDb.Country.Entity.insert(source)
            println("Country ${source.country}")
        }
        DvdRentalCsvLoader.Customer.loadCsv(resource("dvdrental/3055.dat")).forEach { source ->
            DvdRentalDb.Customer.Entity.insert(source)
            println("Customer ${source.firstName} ${source.lastName}")
        }
        //DvdRentalCsvLoader.Film.loadCsv(resource("dvdrental/3061.dat")).forEach { source ->
        //    DvdRentalDb.Film.Entity.insert(source)
        //    println("Film ${source.title}")
        //}
        DvdRentalCsvLoader.FilmActor.loadCsv(resource("dvdrental/3062.dat")).forEach { source ->
            DvdRentalDb.FilmActor.Entity.insert(source)
            println("FilmActor ${source.filmId} ${source.actorId}")
        }
        DvdRentalCsvLoader.FilmCategory.loadCsv(resource("dvdrental/3063.dat")).forEach { source ->
            DvdRentalDb.FilmCategory.Entity.insert(source)
            println("FilmCategory ${source.filmId} ${source.categoryId}")
        }
        DvdRentalCsvLoader.Inventory.loadCsv(resource("dvdrental/3071.dat")).forEach { source ->
            DvdRentalDb.Inventory.Entity.insert(source)
            println("Inventory ${source.filmId} ${source.storeId}")
        }
        DvdRentalCsvLoader.Language.loadCsv(resource("dvdrental/3073.dat")).forEach { source ->
            DvdRentalDb.Language.Entity.insert(source)
            println("Language ${source.name}")
        }
        DvdRentalCsvLoader.Payment.loadCsv(resource("dvdrental/3075.dat")).forEach { source ->
            DvdRentalDb.Payment.Entity.insert(source)
            println("Payment ${source.customerId} ${source.staffId} ${source.rentalId} ${source.paymentDate}")
        }
        DvdRentalCsvLoader.Rental.loadCsv(resource("dvdrental/3077.dat")).forEach { source ->
            DvdRentalDb.Rental.Entity.insert(source)
            println("Rental ${source}")
        }
        DvdRentalCsvLoader.Staff.loadCsv(resource("dvdrental/3079.dat")).forEach { source ->
            DvdRentalDb.Staff.Entity.insert(source)
            println("Staff ${source}")
        }
    }
}
