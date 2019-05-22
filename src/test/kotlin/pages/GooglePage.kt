package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit

class GooglePage(private val driver: WebDriver) {

    private val pageUrl = "https://www.google.ru/"

    init {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES)
        PageFactory.initElements(driver, this)
    }

    //строка поиска
    @FindBy(css = "[maxlength]")
    lateinit var searchString: WebElement

    fun open() {
        driver.get(pageUrl)
    }

    fun search() {
        searchString.submit()
    }
}