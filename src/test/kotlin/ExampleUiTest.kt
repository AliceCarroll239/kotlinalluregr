import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import steps.ExampleUiSteps
import utils.TestUtils

@DisplayName("Google Search Example")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

class ExampleUiTest {
    val driver: WebDriver = TestUtils().configureFirefoxWebDriver(TestUtils().getCurrentWorkingDirectory())

    val googlePage = pages.GooglePage(driver)
    val googleSearchPage = pages.GoogleSearchPage(driver)

    val testSteps = ExampleUiSteps()

    @Order(1)
    @Test
    @DisplayName("Open Google")
    @Description("---")
    @Feature("---")
    fun checkAgentHealthcheck() {
        googlePage.run {
            open()
            assertThat(testSteps.getTitle(driver), `is`("Google"))
        }
    }

    @Order(2)
    @Test
    @DisplayName("Google search")
    @Description("---")
    @Feature("---")
    fun checkGoogleSearch() {
        googlePage.run {
            searchString.sendKeys("Kotlin")
            search()
        }
        googleSearchPage.run {
            assert(returnSearchResults().contains("Kotlin Programming Language"))
        }
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}