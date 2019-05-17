package utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestUtils {

    fun retrofitBuilder(baseURL: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}