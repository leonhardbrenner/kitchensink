package generators

import com.squareup.kotlinpoet.*
import java.io.StringWriter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType

@DslMarker
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.TYPE)
annotation class KotlinPoetDsl

class FileBuilder(
        packageName: String,
        fileName: String,
        block: (@KotlinPoetDsl FileBuilder).() -> Unit
) {

    val builder: FileSpec.Builder = FileSpec.builder(packageName, fileName)

    init {
        apply(block)
    }

    fun Class(name: String, block: ClazzBuilder.() -> Unit) =
        ClazzBuilder(TypeSpec.classBuilder(name), block).build().apply {
            builder.addType(this)
        }

    fun Interface(name: String, block: InterfaceBuilder.() -> Unit) =
        InterfaceBuilder(TypeSpec.interfaceBuilder(name), block).build().apply {
            builder.addType(this)
        }

    fun Function(name: String, block: FunctionBuilder.() -> Unit) =
        FunctionBuilder(FunSpec.builder(name), block).build().apply {
            builder.addFunction(this)
        }

    fun build() = builder.build()
}

class ClazzBuilder(
    private val builder: TypeSpec.Builder,
    block: (@KotlinPoetDsl ClazzBuilder).() -> Unit
) {

    init { apply(block) }

    fun CompanionObject(block: CompanionObjectBuilder.() -> Unit) =
            CompanionObjectBuilder(TypeSpec.companionObjectBuilder(), block).build().apply {
                builder.addType(this)
            }

    fun PrimaryConstructor(block: FunctionBuilder.() -> Unit) =
        FunctionBuilder(FunSpec.constructorBuilder(), block).build().apply {
            builder.primaryConstructor(this)
        }

    fun Annotation(type: KClass<*>, block: AnnotationSpec.Builder.() -> Unit) =
        AnnotationSpec.builder(type.asClassName()).apply(block).build().apply {
            builder.addAnnotation(this)
        }

    fun Property(name: String, type: KType, block: PropertySpec.Builder.() -> Unit = {}) =
        PropertySpec.builder(name, type.asTypeName()).apply(block).build().apply {
            builder.addProperty(this)
        }

    fun Function(name: String, block: FunctionBuilder.() -> Unit) =
        FunctionBuilder(FunSpec.builder(name), block).build().apply {
            builder.addFunction(this)
        }

    fun build() = builder.build()
}

class CompanionObjectBuilder(
        private val builder: TypeSpec.Builder,
        block: (@KotlinPoetDsl CompanionObjectBuilder).() -> Unit
) {

    init { apply(block) }

    fun Function(name: String, block: FunctionBuilder.() -> Unit) =
            FunctionBuilder(FunSpec.builder(name), block).build().apply {
                builder.addFunction(this)
            }

    fun build() = builder.build()
}

class InterfaceBuilder(
    private val builder: TypeSpec.Builder,
    block: (@KotlinPoetDsl InterfaceBuilder).() -> Unit
) {

    init {
        apply(block)
    }

    fun Class(type: KClass<*>, modifiers: List<KModifier> = emptyList(), block: ClazzBuilder.() -> Unit) =
            ClazzBuilder(TypeSpec.classBuilder(type.simpleName!!)
                    .addSuperinterface(type)
                    .addModifiers(modifiers), block).build()
                    .apply {
                        builder.addType(this)
                    }

    fun build() = builder.build()

}

class FunctionBuilder(
    private val builder: FunSpec.Builder,
    block: (@KotlinPoetDsl FunctionBuilder).() -> Unit
) {

    val modifiers = listOf(KModifier.FINAL)

    init { apply(block) }

    inner class Parameter(name: String, type: KType, block: ParameterSpec.Builder.() -> Unit = {}) {
        init {
            builder.addParameter(ParameterSpec.builder(name, type.asTypeName()).addModifiers(modifiers).apply(block).build())
        }
    }

    inner class Body(block: CodeBlock.Builder.() -> Unit) {
        init {
            builder.addCode(CodeBlock.builder().apply(block).build())
        }
    }

    inner class Annotation(type: KClass<*>) {
        init {
            builder.addAnnotation(type)
        }
    }

    fun build() = builder.build()

}

fun main(args: Array<String>) {
    val generatedCode = StringWriter().apply {
        FileBuilder("this.is.my.package", "HelloWorld") {

            Class("Greeter") {

                PrimaryConstructor {
                    Parameter("name", String::class.createType())
                }

                Property("name", String::class.createType()) {
                    initializer("name")
                }

                Function("greet") {
                    Body {
                        addStatement("println(%P)", "Hello, \$name")
                    }
                }
            }

            Function("main") {
                Parameter("args", String::class.createType()) {
                    addModifiers(KModifier.VARARG)
                }

                Body {
                    addStatement("%T(args[0]).greet()", ClassName("", "Greeter"))
                }
            }

        }.build().writeTo(this)
    }.toString()
    println(generatedCode)
"""
    
""".trimIndent()
    /*
package `this`.`is`.my.`package`

import Greeter
import kotlin.String

class Greeter(
  val name: String
) {
  fun greet() {
    println("""Hello, $name""")
  }
}

fun main(vararg args: String) {
  Greeter(args[0]).greet()
}

     */
}