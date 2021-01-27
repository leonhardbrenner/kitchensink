import com.squareup.kotlinpoet.*
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*
import kotlin.reflect.KClass

//This is for native types
class Type(val kClass: KClass<*>, val nullable: Boolean = false)

//TODO - add Ref and Link so we can represent the use of another type and relationship between tables
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
        //TODO - I think Visitor should be Model which takes manifests. Then it could be a nice DSL for calling various.
        Visitor(johnnySeeds).walk()
        generateInterfaces(johnnySeeds)
        generateDtos(johnnySeeds)
    }
}

    fun generateInterfaces(manifest: Element) {
        val file = FileSpec.builder("", "JohnnySeeds.kt").apply {
            addType(
                TypeSpec.interfaceBuilder("JohnnySeeds").apply {
                    manifest.children.forEach { element ->
                        if (element.type==null)
                            addType(
                                TypeSpec.interfaceBuilder(element.name).apply {
                                    element.children.forEach { child ->
                                            addProperty(
                                                PropertySpec.builder(
                                                    child.name,
                                                    child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable)
                                                ).mutable(false).build()
                                            )

                                    }
                                }.build())
                    }
                }.build()
            )
        }.build()
        val writer = File("/home/lbrenner/projects/kitchensink/src/commonMain/kotlin/generated/model")
        file.writeTo(writer)
    }

    fun generateDtos(manifest: Element) {
        val file = FileSpec.builder("", "JohnnySeedsDto.kt")
            .addType(
                TypeSpec.interfaceBuilder("JohnnySeedsDto").apply {
                    manifest.children.forEach { element ->
                        val typeSpec = TypeSpec.classBuilder(element.name)
                            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
                            .addModifiers(KModifier.DATA)
                            .addSuperinterface(ClassName(element.packageName, element.name))
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    element.children.forEach { child ->
                                        addParameter(child.name, child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable)).build()
                                    }
                                }.build()
                            )
                            .apply {
                                element.children.forEach { child ->
                                    val propertySpec = PropertySpec.builder(
                                        child.name,
                                        child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable),
                                        KModifier.OVERRIDE
                                    )
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
        val writer = File("/home/lbrenner/projects/kitchensink/src/commonMain/kotlin/generated/model")
        file.writeTo(writer)
    }
