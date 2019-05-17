import com.google.gson.Gson
import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import utils.DataProvider
import utils.TestParams

@DisplayName("Пример теста")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)

@Execution(ExecutionMode.CONCURRENT)

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
        fun checkGetMethod() {
            val result = stepsAgent.getMethod(testSettings)
            assertThat(result!!.url, `is`("https://httpbin.org/get"))
        }

        @Test
        @DisplayName("POST")
        @Description("---")
        @Feature("Testing different HTTP verbs")
        fun checkPostMethod() {
            val result = stepsAgent.postMethod(testSettings)
            assertThat(result!!.url, `is`("https://httpbin.org/post"))
        }

    }
}