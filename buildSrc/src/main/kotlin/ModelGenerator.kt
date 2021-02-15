import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.*
import models.*
import schema.ManifestNew.Namespace

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }

    interface Fancy {
        val a: A
        val nullableA: A?
        val listOfA: List<A>
        val listOfNullableA: List<A?>
        val nullableListOfA: List<A>?
        val nullableListOfNullableA: List<A?>?
        interface A {
            val b: B
            val nullableB: B?
            val listOfB: List<B>
            val listOfNullableB: List<B?>
            val nullableListOfB: List<B>?
            val nullableListOfNullableB: List<B?>?
            interface B {
                val c: C
                val nullableC: C?
                val listOfC: List<C>
                val listOfNullableC: List<C?>
                val nullableListOfC: List<C>?
                val nullableListOfNullableC: List<C?>?
            }
        }
        interface C {
            val x: Int?
        }
    }

    fun output(namespace: Namespace) {
        namespace.elements.forEach {
            println("Element: ${it.name}, ${it.type.typeName} = minOccurs=${it.minOccurs} maxOccurs=${it.maxOccurs} raw=${it.type.rawType} nullable=${it.type.nullable}")
        }
        fun output(type: Namespace.Type, indent: String = "") {
            println("${indent}ComplexType: ${type.kType}")
            type.elements.forEach {
                println("\t${indent}Element: ${it.name}, ${it.type.typeName} = minOccurs=${it.minOccurs} maxOccurs=${it.maxOccurs} raw=${it.type.rawType} nullable=${it.type.nullable}")
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
        output(Namespace(Fancy::class))

        //listOf(seeds, dvdRentalsNew).forEach { namespace ->
        //    InterfaceGenerator.generate(namespace)
        //    DtoGenerator.generate(namespace)
        //    DbGenerator.generate(namespace)
        //    BuilderGenerator.generate(namespace)
        //}
        //CsvLoaderGenerator.generate(dvdRentalsNew)
    }
}
