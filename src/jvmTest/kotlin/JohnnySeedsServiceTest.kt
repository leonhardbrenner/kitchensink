import org.junit.Test
import java.io.File
import services.JohnnySeedsService

/**
 * Expand out the tests in here
 */
class JohnnySeedsServiceTest {

    @Test
    fun `DetailedSeed deserialize`() {
        JohnnySeedsService.DetailedSeed().fetchAll()
    }

    @Test
    fun `Category deserialize`() {
        JohnnySeedsService.Category().fetchAll()
    }

    @Test
    fun `BasicSeed deserialize`() {
        JohnnySeedsService.BasicSeed().fetchAll()
    }

    @Test
    fun `SeedFacts deserialize`() {
        JohnnySeedsService.SeedFacts().fetch()
    }

}
