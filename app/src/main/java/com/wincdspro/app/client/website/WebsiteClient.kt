package com.wincdspro.app.client.website

import android.os.Handler
import android.os.Looper
import com.wincdspro.app.client.base.BaseClient
import com.wincdspro.app.model.auth.AuthToken
import com.wincdspro.app.util.Mapper
import com.wincdspro.app.util.SettingsManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

open class WebsiteClient : BaseClient(WINCDSPRO_SCHEME, WINCDSPRO_URL, WINCDSPRO_PORT) {
    companion object {
        private const val WINCDSPRO_SCHEME = "http"
        private const val WINCDSPRO_URL = "www.wincdspro.com"
        private const val WINCDSPRO_PORT = 80

        private var host: String = WINCDSPRO_URL
        private var port: Int = WINCDSPRO_PORT

        var additionalInterceptors: List<Interceptor> = listOf()

        private var token: String?
            get() = SettingsManager.authToken.token
            set(v) {
                SettingsManager.authToken = if (v == null) AuthToken() else AuthToken.decode(v)
            }

        var lastRequest: String = ""
        var lastStatusCode: Int = 0
        var lastResponse: String = ""
        var lastError: String = ""

        @JvmStatic
        fun threaded(r: () -> Unit) {
            Handler(Looper.getMainLooper()).post {
                r.invoke()
            }
        }

        @JvmStatic
        fun recordRestError(method: String, url: String, payload: String, response: String): Boolean {
            var content = "\n------------------------------------------------------------------------------\nREST FAILURE:\n$method - $url"
            if (payload != "") content += "\nPAYLOAD:\n--------------\n$payload"
            if (response != "") content += "\nRESPONSE:\n--------------\n$response"
            return try {
                threaded {
                    WebsiteClient().postRestError(content, ::ignoreResponse)
                }
                true
            } catch (e: Exception) {
                false
            }
        }

        protected fun ignoreResponse(status: Int, result: String?): Unit = Unit
    }

    private var clientInternal: Retrofit? = null
    fun refreshClient() {
        clientInternal = null
    }

    val client: Retrofit
        get() {
            if (clientInternal == null) clientInternal = createClient()
            return clientInternal!!
        }

    private val httpClient = OkHttpClient.Builder().build()

    private fun createClient(): Retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(Mapper.getMapper()))
        .build()

    private val apiRestError: RestErrorApi = client.create(RestErrorApi::class.java)

// ////////////////////////////////////////////////////////////////////////////////
// Api Access
// ////////////////////////////////////////////////////////////////////////////////

    fun postRestError(content: String, result: (status: Int, result: String?) -> Unit) =
        enqueueForString(result) { apiRestError.postRestError(content) }
}
