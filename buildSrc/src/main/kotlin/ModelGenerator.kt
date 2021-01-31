import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import schema.Visitor
import generators.InterfaceGenerator
import generators.DtoGenerator
import generators.DbGenerator
import models.johnnySeeds

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }

    @TaskAction
    fun generate() {
        //TODO - I think Visitor should be Model which takes manifests. Then it could be a nice DSL for calling various.
        //Visitor(johnnySeeds).walk()
        InterfaceGenerator.generate(johnnySeeds)
        DtoGenerator.generateDto(johnnySeeds)
        DbGenerator.generate(johnnySeeds)
        //generators.generateWorkflows()
    }

}
