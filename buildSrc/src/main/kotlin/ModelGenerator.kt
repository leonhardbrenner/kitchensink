import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
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
        generateInterface(johnnySeeds)
        generateDto(johnnySeeds)
        generateDb(johnnySeeds)
    }
}

fun generateInterface(manifest: Element) {
    val file = FileSpec.builder("generated.model", "JohnnySeeds").apply {
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
    val writer = File("/home/lbrenner/projects/kitchensink/src/commonMain/kotlin")
    file.writeTo(writer)
}

fun generateDto(manifest: Element) {
    //TODO - consider dropping the generated. prefix. We can keep the files in a different directoru from packagename???
    val file = FileSpec.builder("generated.model", "JohnnySeedsDto")
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
                                    addParameter(
                                        child.name,
                                        child.type!!.kClass.asTypeName().copy(nullable = child.type.nullable)
                                    ).build()
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
                                addFunction(
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
    val writer = File("/home/lbrenner/projects/kitchensink/src/commonMain/kotlin")
    file.writeTo(writer)
}

fun generateDb(manifest: Element) {
    //TODO - consider dropping the generated. prefix. We can keep the files in a different directoru from packagename???
//import org.jetbrains.exposed.sql.ResultRow
//import org.jetbrains.exposed.sql.selectAll
//import org.jetbrains.exposed.sql.transactions.transaction
    val file = FileSpec.builder("generated.model.db", "JohnnySeedsDb")
        .addImport("org.jetbrains.exposed.dao", "IntEntity", "IntEntityClass")
        .addImport("org.jetbrains.exposed.dao.id", "EntityID", "IntIdTable")
        .addImport("org.jetbrains.exposed.sql", "ResultRow", "selectAll")
        .addImport("org.jetbrains.exposed.sql.transactions", "transaction")
        .addImport("generated.model", "JohnnySeedsDto")
        .addType(
            TypeSpec.objectBuilder("JohnnySeedsDb").apply {
                manifest.children.forEach { element ->
                    val typeSpec = TypeSpec.objectBuilder(element.name)
                        .addType(
                            TypeSpec.objectBuilder("Table")
                                .superclass(ClassName("org.jetbrains.exposed.dao.id", "IntIdTable"))
                                .addSuperclassConstructorParameter("%S", element.name)
                                //.superclass(element.type!!.kClass.asTypeName().copy(nullable = element.type.nullable)) //XXX - I need to get this working it has to extend IntIdTable(<table_name>)
                                .apply {
                                    element.children.forEach { child ->
                                        val propertySpec = PropertySpec.builder(
                                            child.name,
                                            ClassName("org.jetbrains.exposed.sql", "Column")
                                                .parameterizedBy(String::class.asTypeName().copy(nullable = child.type!!.nullable))
                                        )
                                            .initializer("text(\"${child.name}\")${if (child.type!!.nullable) ".nullable()" else ""}")
                                            //.mutable(true)
                                            .build()
                                        addProperty(propertySpec)
                                    }
                                }.build()
                        )
                        .addType(
                            TypeSpec.classBuilder("Entity")
                                .superclass(ClassName("org.jetbrains.exposed.dao", "IntEntity"))
                                .addSuperclassConstructorParameter("id")
                                .addSuperinterface(ClassName("generated.model.JohnnySeeds", element.name))
                                .primaryConstructor(
                                    FunSpec.constructorBuilder().apply {
                                        addParameter(
                                            "id",
                                            ClassName("org.jetbrains.exposed.dao.id", "EntityID")
                                                .parameterizedBy(Int::class.asTypeName())
                                        ).build()
                                    }.build()
                                )
                                .addType(
                                    TypeSpec.companionObjectBuilder()
                                        .superclass(
                                            ClassName("org.jetbrains.exposed.dao", "IntEntityClass")
                                                .parameterizedBy(ClassName("", "Entity"))
                                        )
                                        .addSuperclassConstructorParameter("Table")
                                        .build()
                                )
                                //.superclass(element.type!!.kClass.asTypeName().copy(nullable = element.type.nullable)) //XXX - I need to get this working it has to extend IntIdTable(<table_name>)
                                .apply {
                                    //addType(TypeSpec.companionObjectBuilder().superclass(element.type!!.kClass.asTypeName().copy(nullable = element.type.nullable)).build())
                                    element.children.forEach { child ->
                                        val propertySpec = PropertySpec.builder(
                                            child.name,
                                            String::class.asTypeName().copy(nullable = child.type!!.nullable),
                                            KModifier.OVERRIDE
                                        )
                                            //ClassName("org.jetbrains.exposed.sql", "Column")
                                            //    .parameterizedBy(String::class.asTypeName())
                                            .delegate("Table.%L", child.name)
                                            .mutable(true)
                                            //.modifiers(KModifier.OVERRIDE)
                                            .build()
                                        addProperty(propertySpec)
                                    }
                                }
                                .build()
                        )
                        .addFunction(
                            FunSpec.builder("create")
                                .addParameter(
                                    "source",
                                    ClassName("org.jetbrains.exposed.sql", "ResultRow")
                                )
                                //Look at CodeBlock.addArgument and you will see L stands for literal
                                .addCode(
                                    "return %LDto.%L(%L)",
                                    element.parent!!.name,
                                    element.name!!,
                                    element.children.map { "source[Table.${it.name}]" }.joinToString(", ")
                                )
                                .build()
                        )
                        .addFunction(
                            FunSpec.builder("fetchAll")
                                .addCode(
                                    "return transaction { with (Table) { selectAll().map { create(it) } } }"
                                )
                                .build()
                        )
                    addType(typeSpec.build())
                }
            }.build()
        ).build()
    val writer = File("/home/lbrenner/projects/kitchensink/src/jvmMain/kotlin")
    file.writeTo(writer)
}
