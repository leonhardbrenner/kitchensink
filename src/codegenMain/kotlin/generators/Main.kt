package generators

import JohnnySeeds
import com.squareup.kotlinpoet.*
import java.io.File
import java.io.Serializable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

/**
 * Create a task which calls this which has access to common.
 * We will treat this like a plugin and later it may become that.
 * This will read from common as it already does and generate to the sources directory.
 * Make a demon script that monitors for changes in the source interfaces and triggers a rebuild accordingly.
 * Also when we generate we should avoid writing unchanged files and delete stale files.
 * You should get CLI working
 * Gradle task should pass the directory to generate to. I think simply writing to:
 *     (jvm|js)(Main|Test)/src/kotlin/generated
 * Get this step to run before compiling with a doLast in commonMain.
 */
fun main(args: Array<String>) {
    //val reflector = Container(Class.forName("manifest.Manifest").kotlin)
    val reflector = Container(JohnnySeeds::class)
    val file = FileSpec.builder("", "HelloWorld")
        .addType(
            TypeSpec.interfaceBuilder("JohnnySeedsDto").apply {
                reflector.containers.forEach { container ->
                    val typeSpec = TypeSpec.classBuilder(container.source.simpleName!!)
                        //.addAnnotation(Serializable::class)
                        .addModifiers(KModifier.DATA)
                        .addSuperinterface(container.source)
                        .primaryConstructor(
                            FunSpec.constructorBuilder().apply {
                                container.elements.forEach { element ->
                                    addParameter(element.name, element.returnType.asTypeName()).build()
                                }
                            }.build()
                        )

                        .apply {
                            container.elements.forEach { element ->
                                val propertySpec = PropertySpec.builder(element.name, element.returnType.asTypeName(), KModifier.OVERRIDE)
                                    .initializer(element.name)
                                    //.mutable(true)
                                    .build()
                                addProperty(propertySpec)
                            }
                        }
                        .addType(
                            TypeSpec.companionObjectBuilder().apply {
                                val propertySpec = PropertySpec.builder("path", String::class)
                                    .initializer("\"/${container.source.qualifiedName!!.replace('.', '/')}\"")
                                    //.mutable(true)
                                    .build()
                                addProperty(propertySpec)
                                addFunction(
                                    FunSpec.builder("create")
                                        .addParameter("source", container.source.asTypeName())
                                        //Look at CodeBlock.addArgument and you will see L stands for literal
                                        .addCode(
                                            "return %L(%L)",
                                            container.source.simpleName!!,
                                            container.elements.map { "source.${it.name}" }.joinToString(", ")
                                        )
                                        .build()
                                )
                            }.build()
                        )
                        .build()
                    addType(typeSpec)
                }
            }.build()
        ).build()
    val baseDir = "/home/lbrenner/projects/kitchensink/src/jvmMain/kotlin/generated/model"
    val writer = File("$baseDir/JohnnySeedsDto.kt").apply { parentFile.mkdirs() }.bufferedWriter()
    file.writeTo(writer)
    writer.close()
    file.writeTo(System.out)
}

//fun main() {
//    println(JohnnySeedsDto.SeedFacts.create(JohnnySeedsDto.SeedFacts(listOf("Stuff I want to", "know"), "Very old", "Lenny")))
//    println(JohnnySeedsDto.SeedFacts.create(object: Manifest.SeedFacts {
//        override val name = "Len"
//        override val maturity = "Very old"
//        override val facts = listOf("Stuff I want to", "know")
//    }))
//}

class Container <T: Any> (val source: KClass<T>) {

    val containers
        get() = source.nestedClasses.map { Container(it) }

    val elements
        get() = source.members
            .filterNot { Element.filters.contains(it.name) }
            .map { Element(it) }

}

class Element (val source: KCallable<*>) {

    companion object {
        //TODO - Investigate why these are polluting our namespace. I did not think interfaces would have these.
        val filters = listOf("equals", "hashCode", "toString").toSet()
    }

    val name
        get() = source.name

    val returnType
        get() = source.returnType

}
