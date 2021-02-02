import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Test
import services.JohnnySeedsService

/**
 * Expand out the tests in here
 */
class JohnnySeedsServiceTest {
    val service = JohnnySeedsService(ObjectMapper().registerModule(KotlinModule()))

    @Test
    fun `DetailedSeed deserialize`() {
        service.DetailedSeed().fromJson()
    }

    @Test
    fun `Category deserialize`() {
        service.Category().fromJson()
    }

    @Test
    fun `BasicSeed deserialize`() {
        service.BasicSeed().fromJson()
    }

    @Test
    fun `SeedFacts deserialize`() {
        service.SeedFacts().fromJson()
    }

}
