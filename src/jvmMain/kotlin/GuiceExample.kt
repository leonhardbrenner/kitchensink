import com.authzee.kotlinguice4.getInstance
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import javax.inject.Inject

open class TransactionLog {
    fun logChargeResult(result: ChargeResult) {
        println(result)
    }

    fun logConnectException(e: UnreachableException) {
        println("Exception " + e.message)
    }
}

class DatabaseTransactionLog: TransactionLog()
abstract class CreditCardProcessor {
    abstract fun charge(creditCard: CreditCard?, amount: Double): ChargeResult
}

class PaypalCreditCardProcessor: CreditCardProcessor() {
    override fun charge(creditCard: CreditCard?, amount: Double) = ChargeResult(true)
}

data class PizzaOrder(val amount: Double)
data class CreditCard(val number: String)
class Receipt(val amount: Double, val message: String?) {
    companion object {
        fun forSuccessfulCharge(amount: Double) = Receipt(amount, null)
        fun forDeclinedCharge(message: String) = Receipt(0.0, "DECLINED - $message")
        fun forSystemFailure(message: String) = Receipt(0.0, "SYSTEM - $message")
    }
}

class ChargeResult(val success: Boolean) {
    fun wasSuccessful() = success
    fun getDeclineMessage() = "Insufficient Funds"
}

abstract class BillingService {
    abstract fun chargeOrder(order: PizzaOrder, creditCard: CreditCard?): Receipt
}

class RealBillingService @Inject constructor(
    processor: CreditCardProcessor,
    transactionLog: TransactionLog
) : BillingService() {
    private val processor: CreditCardProcessor
    private val transactionLog: TransactionLog
    override fun chargeOrder(order: PizzaOrder, creditCard: CreditCard?): Receipt {
        return try {
            val result: ChargeResult = processor.charge(creditCard, order.amount)
            transactionLog.logChargeResult(result)
            if (result.wasSuccessful()) Receipt.forSuccessfulCharge(order.amount) else Receipt.forDeclinedCharge(
                result.getDeclineMessage()
            )
        } catch (e: UnreachableException) {
            transactionLog.logConnectException(e)
            Receipt.forSystemFailure(e.message!!)
        }
    }

    init {
        this.processor = processor
        this.transactionLog = transactionLog
    }
}

class UnreachableException: Exception("You can't be reached.")

class BillingModule : AbstractModule() {
    override fun configure() {
        bind(TransactionLog::class.java).to(DatabaseTransactionLog::class.java)
        bind(CreditCardProcessor::class.java).to(PaypalCreditCardProcessor::class.java)
        bind(BillingService::class.java).to(RealBillingService::class.java)
    }
}

fun main(args: Array<String>) {
    val injector: Injector = Guice.createInjector(BillingModule())
    val billingService = injector.getInstance<BillingService>()
    println(billingService.chargeOrder(PizzaOrder(8.0), CreditCard("3343-4443-5556-6776")).amount)
}
