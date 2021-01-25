import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import com.squareup.kotlinpoet.*

import java.io.Serializable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

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

interface Manifest {
    interface X {
        val a: Int
        val z: Z
        val y2: Y?
        val z2: Z?
        val y3: List<Y?>?
        val z3: List<Z?>?

        interface Y {
            val b: String
            val z22: List<Z?>?
        }
    }
}

interface Z {
    val c: Double
    val d: List<String>
}

open class Task1 : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }


    @TaskAction
    fun task1() {
        println("Hello from buildSrc")

        //val reflector = Container(Class.forName("manifest.Manifest").kotlin)
        val reflector = Container(Manifest::class)
        val file = FileSpec.builder("", "HelloWorld")
            .addType(
                TypeSpec.interfaceBuilder("JohnnySeedsDto").apply {
                    reflector.containers.forEach { container ->
                        val typeSpec = TypeSpec.classBuilder(container.source.simpleName!!)
                            .addAnnotation(Serializable::class)
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
                                    val propertySpec = PropertySpec.builder(element.name, element.returnType.asTypeName(), KModifier.FINAL)
                                        .initializer(element.name)
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                }
                            }
                            .addFunction(
                                FunSpec.builder("create")
                                    .addParameter("source", container.source.asTypeName())
                                    //Look at CodeBlock.addArgument and you will see L stands for literal
                                    .addCode("return %L(%L)", container.source.simpleName!!, container.elements.map { "source.${it.name}" }.joinToString(", "))
                                    .build()
                            )
                            .addType(
                                TypeSpec.companionObjectBuilder().apply {
                                    val propertySpec = PropertySpec.builder("path", String::class, KModifier.FINAL)
                                        .initializer("\"/${container.source.qualifiedName!!.replace('.', '/')}\"")
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                }.build()
                            )
                            .build()
                        addType(typeSpec)
                    }
                }.build()
            ).build()

        file.writeTo(System.out)
    }
}
