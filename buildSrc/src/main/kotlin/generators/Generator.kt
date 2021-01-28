package generators

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File

interface Generator {
    val path
        get() = "/home/lbrenner/projects/kitchensink/src"
}

