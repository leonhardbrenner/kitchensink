import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGenerator
import generators.DtoGenerator
import generators.CsvLoaderGenerator
import generators.DbGenerator
import models.johnnySeeds
import models.dvdRental

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }

    @TaskAction
    fun generate() {
        listOf(johnnySeeds, dvdRental).forEach { manifest ->
            InterfaceGenerator.generate(manifest)
            DtoGenerator.generateDto(manifest)
            CsvLoaderGenerator.generateCsvLoader(manifest)
            DbGenerator.generate(manifest)
        }
    }
}
