package data

import com.google.gson.annotations.SerializedName

data class NewUserRequest(
    @SerializedName("currency")
    val currency: String = "USD",
    @SerializedName("email")
    val email: String,
    @SerializedName("isEmailConfirmed")
    val isEmailConfirmed: Boolean = false,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String = "tp",
    @SerializedName("socialId")
    val socialId: String = "",
    @SerializedName("socialType")
    val socialType: String = "",
    @SerializedName("tariff")
    val tariff: String = "investing"
)

data class NewUserResponse(
    @SerializedName("authKey")
    val authKey: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("countryInfo")
    val countryInfo: CountryInfo,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("tariff")
    val tariff: String
)

data class CountryInfo(
    @SerializedName("codeA2")
    val codeA2: String,
    @SerializedName("codeA3")
    val codeA3: String,
    @SerializedName("en")
    val en: String,
    @SerializedName("localized")
    val localized: String,
    @SerializedName("phoneCode")
    val phoneCode: String
)
