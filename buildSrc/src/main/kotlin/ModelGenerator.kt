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
        listOf(seeds, dvdRentalsNew).forEach { namespace ->
            InterfaceGenerator.generate(namespace)
            DtoGenerator.generate(namespace)
            DbGenerator.generate(namespace)
            BuilderGenerator.generate(namespace)
        }
        CsvLoaderGenerator.generate(dvdRentalsNew)
    }
}
