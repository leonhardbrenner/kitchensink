package generators

import com.squareup.kotlinpoet.*
import java.io.StringWriter
import kotlin.reflect.KClass

@DslMarker
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.TYPE)
annotation class KotlinPoetDsl

fun kotlinFile(packageName: String, fileName: String, block: (@KotlinPoetDsl FileBuilder).() -> Unit): FileSpec {
    return FileBuilder(packageName, fileName).apply(block).build()
}
class FileBuilder(packageName: String, fileName: String) {

    private val builder: FileSpec.Builder = FileSpec.builder(packageName, fileName)

    inner class Class(name: String, block: ClazzBuilder.() -> Unit) {
        init {
            builder.addType(ClazzBuilder(TypeSpec.classBuilder(name)).apply(block).build())
        }
    }

    inner class Interface(val name: String, val block: InterfaceBuilder.() -> Unit) {
        init {
            builder.addType(InterfaceBuilder(TypeSpec.interfaceBuilder(name)).apply(block).build())
        }
    }

    inner class Function(name: String, block: FunctionBuilder.() -> Unit) {
        init {
            builder.addFunction(FunctionBuilder(FunSpec.builder(name)).apply(block).build())
        }
    }

    fun build(): FileSpec {
        return builder.build()
    }
}

fun kotlinClass(name: String, block: (@KotlinPoetDsl ClazzBuilder).() -> Unit) : TypeSpec {
    return ClazzBuilder(TypeSpec.classBuilder(name)).apply(block).build()
}

class ClazzBuilder(private val builder: TypeSpec.Builder) {

    inner class PrimaryConstructor(block: FunctionBuilder.() -> Unit) {
        init {
            builder.primaryConstructor(FunctionBuilder(FunSpec.constructorBuilder()).apply(block).build())
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
            builder.addFunction(FunctionBuilder(FunSpec.builder(name)).apply(block).build())
        }
    }

    fun build(): TypeSpec {

        return builder.build()
    }
}

class InterfaceBuilder(private val builder: TypeSpec.Builder) {

    inner class Class(name: String, block: ClazzBuilder.() -> Unit) {
        init {
            builder.addType(ClazzBuilder(TypeSpec.classBuilder(name)).apply(block).build())
        }
    }

    inner class DataClass(
        type: KClass<*>,
        modifiers: List<KModifier> = emptyList(),
        block: ClazzBuilder.() -> Unit
    ) {
        init {
            builder.addType(ClazzBuilder(TypeSpec.classBuilder(type.simpleName!!)
                .addSuperinterface(type)
                .addModifiers(modifiers)).apply(block).build())
        }
    }

    fun build(): TypeSpec {
        return builder.build()
    }

}

fun kotlinFunction(name: String, block: (@KotlinPoetDsl FunctionBuilder).() -> Unit) : FunSpec {
    return FunctionBuilder(FunSpec.builder(name)).apply(block).build()
}

class FunctionBuilder(private val function: FunSpec.Builder) {

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
        kotlinFile("this.is.my.package", "HelloWorld") {

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

        }.writeTo(this)
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