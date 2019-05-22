package steps

import org.openqa.selenium.WebDriver

class ExampleUiSteps {

    fun getTitle(driver: WebDriver): String? {
        return driver.title
    }
}