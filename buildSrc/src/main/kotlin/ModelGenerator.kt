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
        listOf(dvdRentalsNew).forEach { namespace ->
            InterfaceGenerator.generate2(namespace)
            DtoGenerator.generate2(namespace)
            DbGenerator2.generate(namespace)
            CsvLoaderGenerator.generate2(namespace)
        }
        listOf(johnnySeeds, dvdRental).forEach { manifest ->
            InterfaceGenerator.generate(manifest)
            DtoGenerator.generate(manifest)
            CsvLoaderGenerator.generate(manifest)
            DbGenerator.generate(manifest)
        }

    }
}
