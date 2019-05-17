package data

import com.google.gson.annotations.SerializedName

data class PostMethodResult(
    @SerializedName("args")
    val args: postArgs,
    @SerializedName("data")
    val `data`: String,
    @SerializedName("files")
    val files: Files,
    @SerializedName("form")
    val form: Form,
    @SerializedName("headers")
    val headers: postHeaders,
    @SerializedName("json")
    val json: Any,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("url")
    val url: String
)

class postArgs(
)

class Files(
)

class Form(
)

data class postHeaders(
    @SerializedName("Accept")
    val accept: String,
    @SerializedName("Accept-Encoding")
    val acceptEncoding: String,
    @SerializedName("Accept-Language")
    val acceptLanguage: String,
    @SerializedName("Content-Length")
    val contentLength: String,
    @SerializedName("Host")
    val host: String,
    @SerializedName("Origin")
    val origin: String,
    @SerializedName("Referer")
    val referer: String,
    @SerializedName("User-Agent")
    val userAgent: String,
    @SerializedName("X-Imforwards")
    val xImforwards: String
)