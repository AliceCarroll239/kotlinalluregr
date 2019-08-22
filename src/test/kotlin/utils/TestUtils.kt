package utils

import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.Path
import java.nio.file.Paths

class TestUtils {

    fun retrofitBuilder(baseURL: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory
                .create())
            .build()
    }

    fun getCurrentWorkingDirectory(): Path {
        return Paths.get(".").toAbsolutePath().normalize()
    }

    fun configureFirefoxWebDriver(driversFolder: Path): FirefoxDriver {
        val firefoxBinary = FirefoxBinary()
        var driverPath = driversFolder
        driverPath = driverPath.resolve("src/test/resources/libs/geckodriver.exe")
        System.setProperty("webdriver.gecko.driver", driverPath.toAbsolutePath().toString())
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.binary = firefoxBinary

        val profile = FirefoxProfile()
        val path = "C:\\Test\\"
        profile.setPreference("browser.download.folderList", 2)
        profile.setPreference("browser.download.dir", path)
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false)
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/x-icon")
        profile.setPreference("browser.download.manager.showWhenStarting", false)
        profile.setPreference("browser.download.manager.focusWhenStarting", false)
        profile.setPreference("browser.download.useDownloadDir", true)
        profile.setPreference("browser.helperApps.alwaysAsk.force", false)
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false)
        profile.setPreference("browser.download.manager.closeWhenDone", true)
        profile.setPreference("browser.download.manager.showAlertOnComplete", false)
        profile.setPreference("browser.download.manager.useWindow", false)
        profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false)
        firefoxOptions.profile = profile
        return FirefoxDriver(firefoxOptions)
    }
}