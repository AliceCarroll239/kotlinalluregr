package interfaces

import data.GetMethodResult
import retrofit2.Call
import retrofit2.http.GET

interface GetMethodResultApi {
    @GET("get")
    fun getMethod(): Call<GetMethodResult>
}