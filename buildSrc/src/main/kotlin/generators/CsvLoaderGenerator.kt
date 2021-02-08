package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schemanew.Namespace

/* This is will generate another Dto(data class) for what ever reason @Serializable polutes the data class with:
   seen1 and serializerConstructor:(
*/
//TODO - try to just the DTO we may need to apply Serializable differently.
object CsvLoaderGenerator: Generator {

    fun generate(namespace: Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}CsvLoader")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}CsvLoader").apply {
                    namespace.complexTypes.forEach { type ->
                        val typeSpec = TypeSpec.classBuilder(type.name)
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
                        addType(typeSpec)
                    }
                }.build()
            ).build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

}
