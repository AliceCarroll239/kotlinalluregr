import com.google.gson.Gson
import interfaces.GetMethodAsync
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import utils.DataProvider
import utils.TestParams
import java.util.concurrent.atomic.AtomicLong
import org.assertj.core.api.ErrorCollector
import java.util.concurrent.CountDownLatch

@DisplayName("Пример теста")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)

//@Execution(ExecutionMode.CONCURRENT)

class ExampleTests {

    val testSettings = Gson().fromJson(
        DataProvider().loadFileAsString(DataProvider().getCurrentWorkingDirectory().resolve("src/test/resources/params.json")),
        TestParams::class.java
    ).baseURL
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
            val succesCount = AtomicLong()
            val failureCount = AtomicLong()
            runBlocking {
                val result = (1..30).map { n->
                    GlobalScope.async {
                        println("Start coruutines $n")
                        if (stepsAgent.getMethodSuspend(testSettings)!!.url == "https://httpbin.org/get") {
                            succesCount.incrementAndGet()
                            println("Get result $n")
                        } else {
                            failureCount.incrementAndGet()
                        }
                    }
                }
                result.awaitAll()
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
            val latch = CountDownLatch(30)

            for (i in 1..30) {
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
