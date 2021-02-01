package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Element
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

object DbGenerator: Generator {
    fun generate(model: Element.Model) {
        model.namespaces.forEach { namespace ->
            val file = FileSpec.builder("generated.model.db", "JohnnySeedsDb")
                .addImport("org.jetbrains.exposed.dao", "IntEntity", "IntEntityClass")
                .addImport("org.jetbrains.exposed.dao.id", "EntityID", "IntIdTable")
                .addImport("org.jetbrains.exposed.sql", "ResultRow", "selectAll")
                .addImport("org.jetbrains.exposed.sql.transactions", "transaction")
                .addImport("generated.model", "JohnnySeedsDto")
                .addType(
                    TypeSpec.objectBuilder("${namespace.name}Db").apply {
                        namespace.types.forEach { complexType ->
                            val typeSpec = TypeSpec.objectBuilder(complexType.name)
                                .addType(
                                    TypeSpec.objectBuilder("Table")
                                        .superclass(ClassName("org.jetbrains.exposed.dao.id", "IntIdTable"))
                                        .addSuperclassConstructorParameter("%S", complexType.name)
                                        .apply {
                                            complexType.slots.forEach { slot ->
                                                val propertySpec = PropertySpec.builder(
                                                    slot.name,
                                                    ClassName("org.jetbrains.exposed.sql", "Column")
                                                        .parameterizedBy(
                                                            String::class.asTypeName()
                                                                .copy(nullable = slot.nullable)
                                                        )
                                                )
                                                    .initializer("text(\"${slot.name}\")${if (slot.nullable) ".nullable()" else ""}")
                                                    .build()
                                                addProperty(propertySpec)
                                            }
                                        }.build()
                                )
                                .addType(
                                    TypeSpec.classBuilder("Entity")
                                        .superclass(ClassName("org.jetbrains.exposed.dao", "IntEntity"))
                                        .addSuperclassConstructorParameter("id")
                                        .addSuperinterface(ClassName("generated.model.JohnnySeeds", complexType.name))
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
                                            complexType.slots.forEach { slot ->
                                                val propertySpec = slot.asPropertySpec(true, KModifier.OVERRIDE)
                                                    .delegate("Table.%L", slot.name)
                                                    .mutable(true)
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
                                        .addCode(
                                            "return %LDto.%L(%L)",
                                            complexType.parent!!.name,
                                            complexType.name!!,
                                            complexType.children.map { "source[Table.${it.name}]" }.joinToString(", ")
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
            val writer = File("$path/jvmMain/kotlin")
            file.writeTo(writer)
        }
    }
}