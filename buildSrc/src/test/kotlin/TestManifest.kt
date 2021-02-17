import org.junit.Test
import schema.Manifest
import models.Flat
import models.Fancy

class TestManifest {
    @Test
    fun test() {
        val flat = Manifest.Namespace(Flat::class)
        val fancy = Manifest.Namespace(Fancy::class)
        flat
    }
}