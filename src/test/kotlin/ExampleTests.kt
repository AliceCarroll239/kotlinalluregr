import com.google.gson.Gson
import interfaces.GetMethodAsync
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import dao.DataProvider
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.CountDownLatch

@DisplayName("Пример теста")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)

//@Execution(ExecutionMode.CONCURRENT)

class ExampleTests {

    val testSettings = DataProvider().testSettings()
    val stepsAgent = steps.ExampleTestsSteps()

    @Nested
    @DisplayName("HTTP Methods")
    inner class Methods {

        @Test
        @DisplayName("GET")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkGetMethodSimple() {
            val succesCount = AtomicLong()
            val failureCount = AtomicLong()

            for (i in 1..10) {
                if (stepsAgent.getMethod(testSettings)!!.url == "https://httpbin.org/get") {
                    succesCount.incrementAndGet()
                } else {
                    failureCount.incrementAndGet()
                }
            }
            println("success $succesCount")
            println("failed $failureCount")
        }

        @Test
        @DisplayName("GET")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkGetMethodAsyncCorut() {
            runBlocking {
                val succesCount = AtomicLong()
                val failureCount = AtomicLong()

                val result = (1..1000).map { n ->
                    GlobalScope.launch {
                        if (stepsAgent.getMethodSuspend(testSettings)!!.url == "https://httpbin.org/get") {
                            succesCount.incrementAndGet()
                        } else {
                            failureCount.incrementAndGet()
                        }
                    }
                }
                result.forEach {
                    it.join()
                }
                println("success $succesCount")
                println("failed $failureCount")
            }
        }

        @Test
        @DisplayName("GET")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkGetMethodAsyncNotCorut() {
            val succesCount = AtomicLong()
            val failureCount = AtomicLong()
            val latch = CountDownLatch(1000)

            for (i in 1..1000) {
                stepsAgent.getMethodAsync(testSettings, object : GetMethodAsync {

                    override fun onRes(result: String) {
                        if (result.equals("https://httpbin.org/get")) {
                            succesCount.incrementAndGet()
                            latch.countDown()
                        } else {
                            failureCount.incrementAndGet()
                            latch.countDown()
                        }
                    }

                    override fun onResFailed() {
                        failureCount.incrementAndGet()
                        latch.countDown()
                    }
                })
            }
            latch.await()
            println("success $succesCount")
            println("failed $failureCount")
        }
    }
}
