import org.junit.Test
import java.io.File

/**
 * Expand out the tests in here
 */
class JohnnySeedsServiceTest {

    @Test
    fun `DetailedSeed deserialize`() {
        val service = JohnnySeedsService.DetailedSeed()
        val obj = service.deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/detailed-seeds.json").file).readText()
        )
        println(obj[0])
    }

    @Test
    fun `Category deserialize`() {
        val service = JohnnySeedsService.Category()
        val obj = service.deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/categories.json").file).readText()
        )
        println(obj[0])
    }

    @Test
    fun `BasicSeed deserialize`() {
        val service = JohnnySeedsService.BasicSeed()
        val obj = service.deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/basic-seeds.json").file).readText()
        )
        println(obj[0])
    }

    @Test
    fun `SeedFacts deserialize`() {
        val service = JohnnySeedsService.SeedFacts()
        val obj = service.deserialize(
            File(ClassLoader.getSystemResource("johnnyseeds/strawberry-seeds.json").file).readText()
        )
        println(obj)
    }

}
