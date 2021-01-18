package generators

import com.squareup.kotlinpoet.*
import java.io.StringWriter
import kotlin.reflect.KClass

@DslMarker
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.TYPE)
annotation class KotlinPoetDsl

class FileBuilder(packageName: String, fileName: String, block: (@KotlinPoetDsl FileBuilder).() -> Unit) {

    val builder: FileSpec.Builder = FileSpec.builder(packageName, fileName)

    init { apply(block) }

    inner class Class(name: String, block: ClazzBuilder.() -> Unit) {
        init {
            builder.addType(ClazzBuilder(TypeSpec.classBuilder(name), block).build())
        }
    }

    inner class Interface(val name: String, val block: InterfaceBuilder.() -> Unit) {
        init {
            builder.addType(InterfaceBuilder(TypeSpec.interfaceBuilder(name), block).build())
        }
    }

    inner class Function(name: String, block: FunctionBuilder.() -> Unit) {
        init {
            builder.addFunction(FunctionBuilder(FunSpec.builder(name), block).build())
        }
    }

    fun build(): FileSpec {
        return builder.build()
    }

}

class ClazzBuilder(
    private val builder: TypeSpec.Builder,
    block: (@KotlinPoetDsl ClazzBuilder).() -> Unit
) {

    init { apply(block) }

    inner class PrimaryConstructor(block: FunctionBuilder.() -> Unit) {
        init {
            builder.primaryConstructor(FunctionBuilder(FunSpec.constructorBuilder(), block).build())
        }
    }

    inner class Annotation(type: KClass<*>, block: AnnotationSpec.Builder.() -> Unit) {
        init {
            builder.addAnnotation(AnnotationSpec.builder(type.asClassName()).apply(block).build())
        }
    }

    inner class Property(name: String, type: KClass<*>, block: PropertySpec.Builder.() -> Unit = {}) {
        init {
            builder.addProperty(PropertySpec.builder(name, type).apply(block).build())
        }
    }

    inner class Function(name: String, block: FunctionBuilder.() -> Unit) {
        init {
            builder.addFunction(FunctionBuilder(FunSpec.builder(name), block).build())
        }
    }

    fun build(): TypeSpec {
        return builder.build()
    }
}

class InterfaceBuilder(
    private val builder: TypeSpec.Builder,
    block: (@KotlinPoetDsl InterfaceBuilder).() -> Unit
) {

    init { apply(block) }

    inner class Class(
        type: KClass<*>,
        modifiers: List<KModifier> = emptyList(),
        block: ClazzBuilder.() -> Unit
    ) {
        init {
            builder.addType(ClazzBuilder(TypeSpec.classBuilder(type.simpleName!!)
                .addSuperinterface(type)
                .addModifiers(modifiers), block).build())
        }
    }

    fun build(): TypeSpec {
        return builder.build()
    }

}

class FunctionBuilder(
    private val function: FunSpec.Builder,
    block: (@KotlinPoetDsl FunctionBuilder).() -> Unit
) {

    init { apply(block) }

    inner class Parameter(name: String, type: KClass<*>, block: ParameterSpec.Builder.() -> Unit = {}) {
        init {
            function.addParameter(ParameterSpec.builder(name, type).apply(block).build())
        }
    }

    inner class Body(block: CodeBlock.Builder.() -> Unit) {
        init {
            function.addCode(CodeBlock.builder().apply(block).build())
        }
    }

    inner class Annotation(type: KClass<*>) {
        init {
            function.addAnnotation(type)
        }
    }

    fun build(): FunSpec {
        return function.build()
    }
}

fun main(args: Array<String>) {
    val generatedCode = StringWriter().apply {
        FileBuilder("this.is.my.package", "HelloWorld") {

            Class("Greeter") {

                PrimaryConstructor {
                    Parameter("name", String::class)
                }

                Property("name", String::class) {
                    initializer("name")
                }

                Function("greet") {
                    Body {
                        addStatement("println(%P)", "Hello, \$name")
                    }
                }
            }

            Function("main") {
                Parameter("args", String::class) {
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