package steps

import data.*
import interfaces.*
import io.qameta.allure.Step
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.TestUtils
import java.io.IOException

class ExampleTestsSteps {

    val testUtils = TestUtils()

    @Step("GetMethod")
    fun getMethod(baseURL: String): GetMethodResult? {
        val request = testUtils.retrofitBuilder(baseURL)
            .create(GetMethodResultApi::class.java).getMethod()
        try {
            val responseGetMethod = request.execute()
            return if (responseGetMethod.code() == 200) {
                responseGetMethod.body()
            } else null
        } catch (e: IOException) {
            return null
        }
    }

    @Step("GetMethodAsync")
    fun getMethodAsync(baseURL: String, GetMethodCallback: GetMethodAsync) {
        val request = testUtils.retrofitBuilder(baseURL)
            .create(GetMethodResultApi::class.java).getMethod()
        val response = request.enqueue(object : Callback<GetMethodResult> {
            override fun onResponse(call: Call<GetMethodResult>, response: Response<GetMethodResult?>) {
                GetMethodCallback.onRes(response.body()!!.url)
            }
            override fun onFailure(call: Call<GetMethodResult>, t: Throwable) {
            }
        })
    }

    @Step("PostMethod")
    fun postMethod(baseURL: String): PostMethodResult? {
        val request = testUtils.retrofitBuilder(baseURL)
            .create(PostMethodApi::class.java).postMethod()
        try {
            val responsePostMethod = request.execute()
            return if (responsePostMethod.code() == 200) {
                responsePostMethod.body()
            } else null
        } catch (e: IOException) {
            return null
        }
    }
}