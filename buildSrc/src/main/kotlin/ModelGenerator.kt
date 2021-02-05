import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.*
import models.*

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }

    @TaskAction
    fun generate() {
        //XXX - when we are done we will delete the lower setup
        listOf(dvdRentalsNew).forEach { namespace ->
            println("Generating faces of: " + namespace!!.name)
            InterfaceGenerator2.generate(namespace)
            //DtoGenerator.generateDto(namespace)
            //CsvLoaderGenerator.generateCsvLoader(namespace)
            //DbGenerator.generate(namespace)
        }
        listOf(johnnySeeds, dvdRental).forEach { manifest ->
            InterfaceGenerator.generate(manifest)
            DtoGenerator.generateDto(manifest)
            CsvLoaderGenerator.generateCsvLoader(manifest)
            DbGenerator.generate(manifest)
        }

    }
}
