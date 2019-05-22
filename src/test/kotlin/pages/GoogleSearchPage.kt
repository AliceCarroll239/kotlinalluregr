package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit

class GoogleSearchPage(private val driver: WebDriver) {

    init {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES)
        PageFactory.initElements(driver, this)
    }

    //вернуть строки
    fun returnSearchResults(): MutableList<String> {
        val searchResults = driver.findElements(By.xpath("//div[@id='rso']//div[@class='rc']//div[@class='r']//a//h3"))
        val returnResult: MutableList<String> = mutableListOf<String>()
        searchResults.forEach {
            returnResult.add(it.getAttribute("innerHTML"))
        }
        return returnResult
    }
}