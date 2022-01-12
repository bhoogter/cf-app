package com.wincdspro.app.client.website

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RestErrorApi {
    companion object {
        private const val SUPPORT = "app-support"
    }

    @POST("$SUPPORT/rest-error.php")
    fun postRestError(@Body content: String): Call<String>
}
