import com.squareup.kotlinpoet.*
import java.io.File

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import org.h2.tools.Csv
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

const val UNBOUNDED = Int.MAX_VALUE

object ManifestNew {

    val namespaceMap = HashMap<String, Namespace>()

    val namespaces get() = namespaceMap.values.toList()

    class Namespace(val kclass: KClass<*>) {
        init {
            namespaceMap[kclass.simpleName!!] = this
        }

        val name get() = kclass.simpleName?:"UNKNOWN $kclass"

        val elements by lazy { kclass.memberProperties.map { Element(this, null, it) } }

        val types by lazy { kclass.nestedClasses.map { Type(this, null, it.createType(), it) } }

        class Element(val namespace: Namespace, val parent: Type?, val property: KProperty<*>) {

            //XXX - I am guessing we only need one or there is more to one of these implementations.
            val dbName = name.toLowerCase()
            val columnName = name.toLowerCase()

            val name get() = property.name

            val type get() = Type(namespace, parent, property.returnType)

            val minOccurs get() = if (type.rawType.isMarkedNullable) 0 else 1

            val maxOccurs get() = if (property.returnType.classifier == List::class) UNBOUNDED else 1

            //TODO - This should be a part of a class of generators.
            fun asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) = PropertySpec.builder(
                name,
                type.typeName
            ).addModifiers(modifiers.toList() ).mutable(mutable)

        }

        class Type(
            val namespace: Namespace, val parent: Type?,
            val kType: KType, val kClass: KClass<*>? = null
        ) {

            //This is because we are generating: Fancy.A.`B?`,
            val name: String get() = if (kClass==null)
                rawType.toString().split(".").last().replace("?", "")
            else
                (kClass.simpleName?:"UNKNOWN2").replace("?", "")

            val parameters get() = kClass!!.primaryConstructor!!.parameters.map { it.name }

            private val memberProperties get()
            = kClass?.memberProperties?:emptyList()
            val elements by lazy { memberProperties.map { Element(namespace, parent, it) } }

            private val nestedClasses get() = kClass?.nestedClasses?:emptyList()
            val types by lazy { nestedClasses.map { Type(namespace, this, it.createType(), it) } }

            val rawType get() = if (kType.classifier == List::class)
                kType.arguments[0].type!!
            else
                kType

            val typeName get() = rawType.asTypeName()

            val className get() = ClassName("generated.model", rawType.toString()
                .replace("?", ""))
                .copy(nullable = nullable)

            val nullable get() = rawType.isMarkedNullable

            val path: String = if (parent==null)
                "/${namespace.name}/$name"
            else
                "${parent.path}/$name"

            fun dotPath(aspect: String = ""): String = if (parent==null)
                "${namespace.name}$aspect.$name"
            else
                "${parent.dotPath(aspect)}.$name"

            val packageName = namespace.name
        }
    }

}

interface Generator {
    val path
        get() = "/home/lbrenner/projects/kitchensink/src"
}

object InterfaceGenerator2: Generator {

    fun generate(namespace: ManifestNew.Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder("${namespace.name}").apply {
            namespace.types.forEach { type ->
                generateType(type)
            }
        }
        val file = FileSpec.builder("generated.model", "${namespace.name}").apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
        file.writeTo(System.out)
    }

    fun TypeSpec.Builder.generateType(type: ManifestNew.Namespace.Type): TypeSpec.Builder = addType(
        TypeSpec.interfaceBuilder(type.name).apply {
            type.elements.forEach { element ->
                addProperty(
                    PropertySpec.builder(
                        element.name,
                        element.type.typeName
                    )
                        .addModifiers(modifiers.toList() )
                        .mutable(false)
                        .build()
                )
            }
            type.types.forEach { type ->
                generateType(type)
            }
        }.build().apply { println("${type.name} created") }
    )
}

object DtoGenerator2: Generator {
    //TODO: Generate the top level elements
    fun generate(namespace: ManifestNew.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto").apply {
                    namespace.types.forEach { generateType(it) }
                }.build()
            ).build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
        file.writeTo(System.out)
    }

    fun TypeSpec.Builder.generateType(type: ManifestNew.Namespace.Type): TypeSpec.Builder = addType(
        TypeSpec.classBuilder(type.name)
            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
            .addModifiers(KModifier.DATA)
            .addSuperinterface(ClassName("generated.model", type.dotPath()))
            .primaryConstructor(
                FunSpec.constructorBuilder().apply {
                    type.elements.forEach { element ->
                        addParameter(element.name, element.type.className).build()
                    }
                }.build()
            )
            .apply {
                type.elements.forEach { element ->
                    val classname = ClassName("generated.model", element.type.rawType.toString().replace("?", ""))
                        .copy(nullable = element.type.nullable)
                    addProperty(
                        PropertySpec.builder(
                            element.name,
                            classname
                        )
                            .addModifiers(listOf(KModifier.OVERRIDE))
                            .mutable(false)
                            .initializer(element.name)
                            .build()
                    )
                }
            }
            .addType(
                TypeSpec.companionObjectBuilder().apply {
                    val propertySpec = PropertySpec.builder("path", String::class)
                        .initializer("\"${type.path}\"")
                        .build()
                    addProperty(propertySpec)
                    addFunction(
                        FunSpec.builder("create")
                            .addParameter("source", ClassName("generated.model", type.dotPath()))
                            //Look at CodeBlock.addArgument and you will see L stands for literal
                            .addCode(
                                "return %T(%L)",
                                ClassName("generated.model", type.dotPath("Dto")),
                                type.elements.map { "source.${it.name}" }.joinToString(", ")
                            )
                            .build()
                    )
                }.build()
            ).apply {
                type.types.forEach {
                    generateType(it)
                }
            }.build()
    )
}

object CsvLoaderGenerator2: Generator {

    fun generate(namespace: ManifestNew.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}CsvLoader")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}CsvLoader").apply {
                    namespace.types.forEach { type ->
                        generateType(type)
                    }
                }.build()
            ).build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
        file.writeTo(System.out)
    }

    fun TypeSpec.Builder.generateType(type: ManifestNew.Namespace.Type): TypeSpec.Builder = addType(
        TypeSpec.classBuilder(type.name)
            .addModifiers(KModifier.DATA)
            .addSuperinterface(ClassName(type.packageName, type.name))
            .primaryConstructor(
                FunSpec.constructorBuilder().apply {
                    type.elements.forEach { slot ->
                        addParameter(
                            slot.name,
                            slot.type.typeName
                        ).build()
                    }
                }.build()
            )
            .apply {
                type.elements.forEach { slot ->
                    addProperty(
                        //TODO - make extension function
                        slot.asPropertySpec(false, KModifier.OVERRIDE)
                            .initializer(slot.name).build()
                    )
                }
            }
            .addType(
                TypeSpec.companionObjectBuilder().apply {
                    addProperty(
                        PropertySpec.builder("header", String::class)
                            .initializer("%S", type.elements.map { it.name }.joinToString("\t"))
                            .build()
                    )
                    addFunction(
                        FunSpec.builder("loadCsv")
                            .addParameter("file", File::class)
                            .addCode("return model.loadCsv<${type.name}>(file, header)")
                            .build()
                    )
                }.build()
            )
            .build()
    )

}

object DbGenerator2: Generator {

    fun generate(namespace: ManifestNew.Namespace) {
        val file = FileSpec.builder("generated.model.db", "${namespace.name}Db")
            .addImport("org.jetbrains.exposed.dao", "IntEntity", "IntEntityClass")
            .addImport("org.jetbrains.exposed.dao.id", "EntityID", "IntIdTable")
            .addImport("org.jetbrains.exposed.sql", "ResultRow", "selectAll")
            .addImport("org.jetbrains.exposed.sql.transactions", "transaction")
            .addImport("generated.model", "${namespace.name}")
            .addImport("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.objectBuilder("${namespace.name}Db").apply {
                    namespace.types.forEach { complexType ->
                        val typeSpec = TypeSpec.objectBuilder(complexType.name)
                            .addType(complexType.table)
                            .addType(complexType.entity)
                            .addFunction(complexType.create)
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
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
        file.writeTo(System.out)
    }

    val ManifestNew.Namespace.Element.propertySpec
        get() = com.squareup.kotlinpoet.PropertySpec.builder(
            name,
            ClassName("org.jetbrains.exposed.sql", "Column")
                .parameterizedBy(type.typeName)
        )
            .apply {
                type.name
            }
            .initializer("${
                when (type.kType.toString()) {
                    "kotlin.String" -> "text"
                    "kotlin.Int" -> "integer"
                    "kotlin.Int" -> "integer"
                    "kotlin.Long" -> "long"
                    "kotlin.Boolean" -> "bool"
                    else -> "text"
                }
            }(\"${dbName}\")${if (type.nullable) ".nullable()" else ""}")
            .build()

    val ManifestNew.Namespace.Type.table
        get() = com.squareup.kotlinpoet.TypeSpec.objectBuilder("Table")
            .superclass(ClassName("org.jetbrains.exposed.dao.id", "IntIdTable"))
            .addSuperclassConstructorParameter("%S", name)
            .apply {
                elements.forEach { element ->
                    addProperty(element.propertySpec) //XXX - bring me back.
                }
            }.build()

    val ManifestNew.Namespace.Type.create
        get() = FunSpec.builder("create")
            .addParameter("source", ClassName("org.jetbrains.exposed.sql", "ResultRow"))

            .addCode("return %LDto.%L(%L)",
                packageName, name, elements.map { "source[Table.${it.name}]" }.joinToString(", "))
            .build()

    val ManifestNew.Namespace.Type.entity
        get() = TypeSpec.classBuilder("Entity")
            .superclass(ClassName("org.jetbrains.exposed.dao", "IntEntity"))
            .addSuperclassConstructorParameter("id")
            .addSuperinterface(ClassName("generated.model.${namespace.name}", name))
            .primaryConstructor(
                FunSpec.constructorBuilder().apply {
                    addParameter(
                        "id",
                        ClassName("org.jetbrains.exposed.dao.id", "EntityID")
                            .parameterizedBy(kotlin.Int::class.asTypeName())
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
                    .addFunction(
                        com.squareup.kotlinpoet.FunSpec.builder("insert")
                            .addParameter(
                                ParameterSpec("source", ClassName(packageName, name))
                            )
                            .addCode("""Entity.new {
                            |%L
                            |}""".trimMargin(), elements.map { "  ${it.columnName} = source.${it.name}" }.joinToString("\n"))
                            .build()
                    )
                    .build()
            )
            .apply {
                elements.forEach { slot ->
                    val propertySpec = slot.asPropertySpec(true, com.squareup.kotlinpoet.KModifier.OVERRIDE)
                        .delegate("Table.%L", slot.name)
                        .mutable(true)
                        .build()
                    addProperty(propertySpec)
                }
            }
            .build()

}

object BuilderGenerator2: Generator {

    fun generate(namespace: ManifestNew.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Builder")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Builder").apply {
                    namespace.types.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
                            .primaryConstructor(
                                FunSpec.constructorBuilder().apply {
                                    type.elements.forEach { element ->
                                        addParameter(element.name, element.type.typeName.copy(nullable = true)).build()
                                    }
                                }.build()
                            )
                            .apply {
                                type.elements.forEach { element ->
                                    addProperty(
                                        //TODO - make extension function
                                        PropertySpec.builder(
                                            element.name,
                                            element.type.typeName.copy(nullable = true)
                                        ).mutable(true)
                                            .initializer(element.name).build()
                                    )
                                }
                            }
                            .addFunction(
                                FunSpec.builder("build")
                                    .returns(ClassName("generated.model", "${type.namespace.name}.${type.name}"))
                                    .addCode(
                                        "return %L(\n%L\n)",
                                        "${type.namespace.name}Dto.${type.name}",
                                        type.elements.map {
                                            if (it.type.nullable)
                                                it.name
                                            else
                                                "${it.name} ?: throw IllegalArgumentException(\"${it.name} is not nullable\")"
                                        }.joinToString(",\n")
                                    )
                                    .build()
                            )

                            .build()
                        addType(typeSpec)
                    }
                }.build()
            ).build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

}

interface Flat {
    interface A {
        val int: Int
        val string: String
        val long: Long
    }
}

interface Fancy {
    val a: A
    val nullableA: A?
    val listOfA: List<A>
    val listOfNullableA: List<A?>
    val nullableListOfA: List<A>?
    val nullableListOfNullableA: List<A?>?
    interface A {
        //val int: Int
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

interface Seeds {
    class BasicSeed(
        val name: String,
        val secondary_name: String,
        val description: String?,
        val image: String,
        val link: String
    )

    class SeedCategory(
        val name: String,
        val image: String,
        val link: String
    )

    class DetailedSeed(
        val name: String,
        val maturity: String?,
        val secondary_name: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    class SeedFacts(
        val name: String,
        //TODO - This should be a list
        val facts: String?,
        val maturity: String?
    )
}

/**
 * Sadly I can't figure out how to debug buildSrc yet. So I made this copy to run in the debugger. Don't use it!!!
 */
class Playground {

    @Test
    fun test() {
        val seeds = ManifestNew.Namespace(Seeds::class)

        val flat = ManifestNew.Namespace(Flat::class)
        //val fancy = ManifestNew.Namespace(Fancy::class)
        InterfaceGenerator2.generate(flat)
        //InterfaceGenerator2.generate(fancy)
        DtoGenerator2.generate(flat)
        //DtoGenerator2.generate(fancy)
        CsvLoaderGenerator2.generate(flat) //NOTE - CsvLoader(s) could be considered flat and not require fancy test
        DbGenerator2.generate(flat) //NOTE - CsvLoader(s) could be considered flat and not require fancy test
        BuilderGenerator2.generate(flat)
    }
}