package generators

import schema.ManifestNew

interface Generator {

    fun generate(namespace: ManifestNew.Namespace)

    val path
        get() = "/home/lbrenner/projects/kitchensink/src"

}

