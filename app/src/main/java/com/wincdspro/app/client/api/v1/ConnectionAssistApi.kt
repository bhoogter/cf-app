package com.wincdspro.app.client.api.v1

import com.wincdspro.app.model.wincds.connect.ConnectionAssistCollection
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnectionAssistApi {
    companion object {
        private const val WINCDSPRO_BASE_URL = "http://wincdspro.com/"
        private const val WINCDSPRO_CONNECT_URL = "http://wincdspro.com/connect/connect.php"

        private const val P_NAME = "name"
        private const val P_HOST = "host"
        private const val P_PORT = "port"
        private const val P_PASS = "pass"
        private const val P_POST = "post"
        private const val P_DELE = "delete"
    }

    @GET(WINCDSPRO_CONNECT_URL)
    fun postConnectionAssist(
        @Query(P_NAME) name: String,
        @Query(P_HOST) host: String,
        @Query(P_PORT) port: String,
        @Query(P_PASS) pass: String,
        @Query(P_POST) post: String = "1",
    ): Call<String>

    @GET(WINCDSPRO_CONNECT_URL)
    fun getConnectionAssist(): Call<ConnectionAssistCollection>

    @DELETE(WINCDSPRO_CONNECT_URL)
    fun deleteConnectionAssist(@Query(P_DELE) delete: String = "1"): Call<String>
}
