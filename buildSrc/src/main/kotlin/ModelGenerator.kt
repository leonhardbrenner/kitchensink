import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGenerator
import generators.DtoGenerator
import generators.DbGenerator
import generators.BuilderGenerator
import generators.CsvLoaderGenerator

import models.seeds
import models.dvdRentalsNew

import schema.Manifest.Namespace

//Test case for things like tables, csv, and all other 2D structures
interface Flat {
    class A(
        val string: String,
        val int: Int,
        val double: Double,
        val long: Long,
        val boolean: Boolean
    )
}

//Test case for things like json
interface Fancy {
    val a: A
    val nullableA: A?
    val listOfA: List<A>
    val listOfNullableA: List<A?>
    val nullableListOfA: List<A>?
    val nullableListOfNullableA: List<A?>?
    class A(
        val b: B,
        val nullableB: B?,
        val listOfB: List<B>,
        val listOfNullableB: List<B?>,
        val nullableListOfB: List<B>?,
        val nullableListOfNullableB: List<B?>?) {
        class B(
            val c: C,
            val nullableC: C?,
            val listOfC: List<C>,
            val listOfNullableC: List<C?>,
            val nullableListOfC: List<C>?,
            val nullableListOfNullableC: List<C?>?
        )
    }
    class C(
        val x: Int?
    )
}

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }

    fun output(namespace: Namespace) {
        namespace.elements.forEach {
            println("Element: ${it.name}, ${it.type.typeName} = " +
                    "minOccurs=${it.minOccurs} maxOccurs=${it.maxOccurs} " +
                    "raw=${it.type.rawType} " +
                    "nullable=${it.type.nullable}")
        }
        fun output(type: Namespace.Type, indent: String = "") {
            println("${indent}ComplexType: ${type.kType}")
            type.elements.forEach {
                println("\t${indent}Element: ${it.name}, ${it.type.typeName} = " +
                        "minOccurs=${it.minOccurs} maxOccurs=${it.maxOccurs} " +
                        "raw=${it.type.rawType} " +
                        "nullable=${it.type.nullable}")
            }
            type.types.forEach {
                output(it, "$indent\t")
            }
        }
        namespace.types.forEach {
            output(it)
        }
    }

    @TaskAction
    fun generate() {

        val flat = Namespace(Flat::class)
        InterfaceGenerator.generate(flat)
        DtoGenerator.generate(flat)
        DbGenerator.generate(flat)
        BuilderGenerator.generate(flat)
        CsvLoaderGenerator.generate(flat)

        val fancy = Namespace(Fancy::class)
        InterfaceGenerator.generate(fancy)
        DtoGenerator.generate(fancy)

        listOf(seeds, dvdRentalsNew).forEach { namespace ->
            InterfaceGenerator.generate(namespace)
            DtoGenerator.generate(namespace)
            DbGenerator.generate(namespace)
            BuilderGenerator.generate(namespace)
        }

        CsvLoaderGenerator.generate(dvdRentalsNew)
    }
}
