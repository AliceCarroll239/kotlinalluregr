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
        fun checkGetMethodAsyncCorut() {
            val count = AtomicLong()
            runBlocking {
                val result = (1..1000).map { n ->
                    GlobalScope.async {
                        try {
                            assert(stepsAgent.getMethod(testSettings)!!.url.equals("https://httpbin.org/get"))
                            //assertThat(stepsAgent.getMethod(testSettings)!!.url, `is`("https://httpbin.org/get"))
                        } catch (e: java.lang.AssertionError) {
                            count.incrementAndGet()
                        }
                    }
                }
                result.awaitAll()
                println(count.get())
            }
        }

        @Test
        @DisplayName("GET")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkGetMethodNotAsync() {
            for (i in 1..100) {
                assertThat(stepsAgent.getMethod(testSettings)!!.url, `is`("https://httpbin.org/get"))
            }
        }

        @Test
        @DisplayName("POST")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkPostMethod() {
            val result = stepsAgent.postMethod(testSettings)
            assertThat(result!!.url, `is`("https://httpbin.org/post"))
        }

        //Пример ассинхронного вызова с callback
        //в тесте использовать вряд ли получится
        // как вернуть результат в тестовый метод непонятно
        @Test
        @DisplayName("GETasync")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkGetMethodAsync() {
            stepsAgent.getMethodAsync(testSettings, object : GetMethodAsync {
                override fun onRes(result: String) {
                    println(result)
                }

                override fun onResFailed() {
                    println("Тут упало")
                }
            })
        }
    }
}
