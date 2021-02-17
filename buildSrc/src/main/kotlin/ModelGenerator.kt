import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGenerator
import generators.DtoGenerator
import generators.DbGenerator
import generators.BuilderGenerator
import generators.CsvLoaderGenerator

import models.Fancy
import models.Flat

import models.seeds
import models.dvdRentalsNew

import schema.Manifest.Namespace

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generate"
    }

    @TaskAction
    fun generate() {

        val flat = Namespace(Flat::class)
        InterfaceGenerator.generate(flat)
        DtoGenerator.generate(flat)
        BuilderGenerator.generate(flat)
        DbGenerator.generate(flat)
        CsvLoaderGenerator.generate(flat)

        val fancy = Namespace(Fancy::class)
        InterfaceGenerator.generate(fancy)
        DtoGenerator.generate(fancy)
        //XXX - BuilderGenerator.generate(fancy)

        listOf(seeds, dvdRentalsNew).forEach { namespace ->
            InterfaceGenerator.generate(namespace)
            DtoGenerator.generate(namespace)
            DbGenerator.generate(namespace)
            BuilderGenerator.generate(namespace)
        }

        CsvLoaderGenerator.generate(dvdRentalsNew)
    }
}
