package data

import com.google.gson.annotations.SerializedName

data class GetMethodResult(
    @SerializedName("args")
    val args: Args,
    @SerializedName("headers")
    val headers: Headers,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("url")
    val url: String
)

class Args

data class Headers(
    @SerializedName("Accept")
    val accept: String,
    @SerializedName("Accept-Encoding")
    val acceptEncoding: String,
    @SerializedName("Cache-Control")
    val cacheControl: String,
    @SerializedName("Content-Type")
    val contentType: String,
    @SerializedName("Host")
    val host: String,
    @SerializedName("Postman-Token")
    val postmanToken: String,
    @SerializedName("User-Agent")
    val userAgent: String,
    @SerializedName("X-Imforwards")
    val xImforwards: String
)