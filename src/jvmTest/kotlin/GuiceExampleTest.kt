import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class GuiceExampleTest {
    @Test
    fun test() {
        val amount = 8.0
        val creditCard = CreditCard("3343-4443-5556-6776")

        val processor = mockk<CreditCardProcessor> {
            every {
                charge(creditCard, amount)
            }.returns(ChargeResult(true))
        }
        val transactionLog = mockk<TransactionLog> {
            every {
                logChargeResult(any())
            }.returns(Unit)
        }
        val billingService = RealBillingService(processor, transactionLog)
        println(billingService.chargeOrder(PizzaOrder(amount), creditCard).amount)
    }
}