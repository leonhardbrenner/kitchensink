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
        group = "com.buckysoap"
        description = "Generate Aspects of our model."
    }

    @TaskAction
    fun generate() {
        //TODO - I think Visitor should be Model which takes manifests. Then it could be a nice DSL for calling various.
        listOf(johnnySeeds, dvdRental).forEach { manifest ->
            InterfaceGenerator.generate(manifest)
            DtoGenerator.generateDto(manifest)
            CsvLoaderGenerator.generateCsvLoader(manifest)
            DbGenerator.generate(manifest)
        }
    }
}
