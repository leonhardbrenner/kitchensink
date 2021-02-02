package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

object DbGenerator: Generator {

    val Element.Model.Slot.propertySpec
    get() = PropertySpec.builder(
        name,
        ClassName("org.jetbrains.exposed.sql", "Column")
            .parameterizedBy(type!!.asTypeName().copy(nullable = nullable))
    )
        .initializer("${
            when (type!!.qualifiedName) {
                "kotlin.String" -> "text"
                "kotlin.Int" -> "integer"
                "kotlin.Double" -> "double"
                "kotlin.Boolean" -> "bool"
                else -> "text"
            }
        }(\"${name}\")${if (nullable) ".nullable()" else ""}")
        .build()

    val Element.Model.Type.table
    get() = TypeSpec.objectBuilder("Table")
        .superclass(ClassName("org.jetbrains.exposed.dao.id", "IntIdTable"))
        .addSuperclassConstructorParameter("%S", name)
        .apply {
            slots.forEach { slot ->
                addProperty(slot.propertySpec)
            }
        }.build()

    val Element.Model.Type.create
        get() = FunSpec.builder("create")
            .addParameter("source", ClassName("org.jetbrains.exposed.sql", "ResultRow"))
            .addCode("return %LDto.%L(%L)",
                parent!!.name, name!!, children.map { "source[Table.${it.name}]" }.joinToString(", "))
            .build()

    val Element.Model.Type.entity
    get() = TypeSpec.classBuilder("Entity")
        .superclass(ClassName("org.jetbrains.exposed.dao", "IntEntity"))
        .addSuperclassConstructorParameter("id")
        .addSuperinterface(ClassName("generated.model.${namespace.name}", name))
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
        .apply {
            slots.forEach { slot ->
                val propertySpec = slot.asPropertySpec(true, KModifier.OVERRIDE)
                    .delegate("Table.%L", slot.name)
                    .mutable(true)
                    .build()
                addProperty(propertySpec)
            }
        }
        .build()

    fun generate(namespace: Element.Model.Namespace) {
        val file = FileSpec.builder("generated.model.db", "${namespace.name}Db")
            .addImport("org.jetbrains.exposed.dao", "IntEntity", "IntEntityClass")
            .addImport("org.jetbrains.exposed.dao.id", "EntityID", "IntIdTable")
            .addImport("org.jetbrains.exposed.sql", "ResultRow", "selectAll")
            .addImport("org.jetbrains.exposed.sql.transactions", "transaction")
            .addImport("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.objectBuilder("${namespace.name}Db").apply {
                    namespace.types.forEach { complexType ->
                        val typeSpec = TypeSpec.objectBuilder(complexType.name)
                            .addType(complexType.table)
                            .addType(complexType.entity)
                            .addFunction(complexType.create
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
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

}