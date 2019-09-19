package interfaces

import data.NewUserRequest
import data.NewUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NewUserApi {
    @POST("users")
    @Headers(
        "Content-Type: application/json; charset=urf-8",
        "Content-Length: User-Agent: FBS_Trader_Android_Client",
        "X-User-Platform: android_app",
        "X-User-Language: en",
        "X-User-Role: tp")
    fun postMethod(@Body newUser:NewUserRequest): Call<NewUserResponse>
}