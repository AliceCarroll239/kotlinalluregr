import com.google.gson.Gson
import interfaces.GetMethodAsync
import utils.DataProvider
import utils.TestParams
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val testSettings = Gson().fromJson(
        DataProvider().loadFileAsString(DataProvider().getCurrentWorkingDirectory().resolve("src/test/resources/params.json")),
        TestParams::class.java
    ).baseURL
    val stepsAgent = steps.ExampleTestsSteps()

    val successCount = AtomicLong()
    val failureCount = AtomicLong()
    val failureOnResCount = AtomicLong()

    for (i in 1..1000) {
        stepsAgent.getMethodAsync(testSettings, object : GetMethodAsync {

            override fun onRes(result: String) {
                if (result.equals("https://httpbin.org/get")) {
                    successCount.incrementAndGet()
                } else {
                    failureCount.incrementAndGet()
                }
            }

            override fun onResFailed() {
                failureOnResCount.incrementAndGet()
            }
        })
    }

    println("success $successCount")
    println("failed $failureCount")
    println("failedOnRes $failureOnResCount")
}