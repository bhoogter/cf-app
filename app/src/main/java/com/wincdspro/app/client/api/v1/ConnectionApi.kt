package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase.Companion.API_V1
import com.wincdspro.app.model.auth.AuthResponse
import com.wincdspro.app.model.settings.AllSettings
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.POST
import retrofit2.http.Path

interface ConnectionApi {
    @GET("${API_V1}verify")
    fun verify(): Call<String>

    @GET("${API_V1}help")
    fun help(): Call<String>

    @POST("${API_V1}authorize")
    fun authorize(@Body body: String): Call<AuthResponse?>

    @GET("${API_V1}connection-test")
    fun connectionTest(): Call<String>

    @GET("${API_V1}settings")
    fun getSettings(): Call<AllSettings?>

    @GET("${API_V1}fx/{filename}")
    fun getFxFile(@Path("filename") filename: String): Call<ResponseBody>

    @HEAD("${API_V1}fx/{filename}")
    fun headFxFile(@Path("filename") filename: String): Call<Void>

    @GET("${API_V1}fx/logo/{storeNo}")
    fun getStoreLogo(@Path("storeNo") storeNo: Int): Call<ResponseBody>

    @HEAD("${API_V1}fx/logo/{storeNo}")
    fun headStoreLogo(@Path("storeNo") storeNo: Int): Call<Void>
}
