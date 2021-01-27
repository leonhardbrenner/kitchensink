import com.squareup.kotlinpoet.*
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.util.*
import kotlin.reflect.KClass

//This is for native types
class Type(val kClass: KClass<*>, val isNullable: Boolean? = false)

class Element(val parent: Element?, val name: String, val type: Type? = null, val block: (Element).() -> Unit = {}) {
    val children: MutableList<Element>

    init {
        children = LinkedList()
        if (parent!=null)
            parent.children.add(this)
        block(this)
    }

    fun Element(name: String, type: Type? = null, block: (Element).() -> Unit = {}) =
        Element(this, name, type, block)

    val path: String
    get() = if (parent == null) "/$name" else "${parent.path}/$name"

    val packageName: String
    get() = parent?.path?.substring(1)?.replace("/", ".")?:""
}

fun Manifest(name: String, block: (Element).() -> Unit) =
    Element(null, name, null, block)

class Visitor(val root: Element) {
    fun walk(element: Element = root) {
        println(element.path)
        element.children.forEach {
            walk(it)
        }
    }
}

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.kotlinexpertise"
        description = "task1"
    }

    @TaskAction
    fun generate() {
        println("Hello from buildSrc")

        val manifest = Manifest("JohnnySeeds") {
            Element("DetailedSeeds") {
                Element("name", Type(String::class))
                Element("maturity", Type(String::class))
                Element("secondary_name", Type(String::class, true))
                Element("description", Type(String::class, true))
                Element("image", Type(String::class, true))
                Element("link", Type(String::class, true))
            }
        }
        Visitor(manifest).walk()
        
        val file = FileSpec.builder("", "HelloWorld")
            .addType(
                TypeSpec.interfaceBuilder("JohnnySeedsDto").apply {
                    manifest.children.forEach { element ->
                        val typeSpec = TypeSpec.classBuilder(element.name)
                            .addAnnotation(ClassName("java.lang", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(element.packageName, element.name))
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    element.children.forEach { child ->
                                        addParameter(child.name, child.type!!.kClass.asTypeName()).build()
                                    }
                                }.build()
                            )
                            .apply {
                                element.children.forEach { child ->
                                    val propertySpec = PropertySpec.builder(child.name, child.type!!.kClass.asTypeName(), KModifier.FINAL)
                                        .initializer(child.name)
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                }
                            }
                            .addType(
                                TypeSpec.companionObjectBuilder().apply {
                                    val propertySpec = PropertySpec.builder("path", String::class, KModifier.FINAL)
                                        .initializer("\"${element.path}\"")
                                        //.mutable(true)
                                        .build()
                                    addProperty(propertySpec)
                                        .addFunction(
                                            FunSpec.builder("create")
                                                .addParameter("source", ClassName(element.packageName, element.name))
                                                //Look at CodeBlock.addArgument and you will see L stands for literal
                                                .addCode(
                                                    "return %L(%L)",
                                                    element.name!!,
                                                    element.children.map { "source.${it.name}" }.joinToString(", ")
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
        file.writeTo(System.out)
    }
}
