package interfaces

import data.PostMethodResult
import retrofit2.Call
import retrofit2.http.POST

interface PostMethodApi {
    @POST("post")
    fun postMethod(): Call<PostMethodResult>
}