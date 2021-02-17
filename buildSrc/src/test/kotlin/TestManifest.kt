import org.junit.Test
import schema.Manifest
import models.Flat
import models.Fancy
import models.DvdRental
import generators.InterfaceGenerator

class TestManifest {
    @Test
    fun test() {
        val dvdRental = Manifest.Namespace(DvdRental::class)
        InterfaceGenerator.generate(dvdRental)

        val flat = Manifest.Namespace(Flat::class)
        InterfaceGenerator.generate(flat)

        val fancy = Manifest.Namespace(Fancy::class)
        InterfaceGenerator.generate(fancy)
    }
}