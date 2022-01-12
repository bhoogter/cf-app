package com.wincdspro.app.client.base

import android.os.Handler
import android.os.Looper
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.model.auth.AuthToken
import com.wincdspro.app.util.Mapper
import com.wincdspro.app.util.SettingsManager
import com.wincdspro.app.util.WLogger
import okhttp3.Interceptor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

open class BaseClient(
    lScheme: String,
    lHost: String,
    lPort: Int,
) {
    companion object {
        const val HTTP = "http"
        const val HTTPS = "https"

        private const val DEFAULT_PORT = 8080

        @JvmStatic
        protected var scheme: String = "http"

        @JvmStatic
        protected var host: String = ""

        @JvmStatic
        protected var port: Int = DEFAULT_PORT

        var additionalInterceptors: List<Interceptor> = listOf()

        private var token: String?
            get() = SettingsManager.authToken.token
            set(v) {
                SettingsManager.authToken = if (v == null) AuthToken() else AuthToken.decode(v)
            }

        var lastRequest: String = ""
        var lastStatusCode: Int = DEFAULT_PORT
        var lastResponse: String = ""
        var lastError: String = ""

        @JvmStatic
        fun threaded(r: () -> Unit) {
            Handler(Looper.getMainLooper()).post {
                r.invoke()
            }
        }
    }

    init {
        scheme = lScheme
        host = lHost
        port = lPort
    }

    protected fun getBaseUrl(s: String = "") = "$scheme://$host:$port${if (s.isEmpty()) "" else "/$s"}"

// ////////////////////////////////////////////////////////////////////////////////
// Retrofit
// ////////////////////////////////////////////////////////////////////////////////

    protected open fun <B> enqueueForObject(result: (status: Int, result: B?) -> Unit, callFactory: () -> Call<B>) {
        val call = callFactory.invoke()
        call.enqueue(object : Callback<B> {
            override fun onResponse(call: Call<B>, response: Response<B>) {
                val statusCode = response.code()
                val body: B? = response.body()
                if (authCheck(statusCode, { enqueueForObject(result, callFactory) }, { result.invoke(HTTP_UNAUTHORIZED, null) })) {
                    recordSuccess(call, statusCode, body)
                    result.invoke(statusCode, body)
                } else Unit
            }

            override fun onFailure(call: Call<B>, t: Throwable) {
                recordFailure(t.message ?: "<NO MESSAGE>", t, call)
                result.invoke(0, null)
            }
        })
    }

    protected open fun enqueueForString(result: (status: Int, result: String?) -> Unit, callFactory: () -> Call<String>) {
        val call = callFactory.invoke()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val statusCode = response.code()
                val body: String? = response.body()
                if (authCheck(statusCode, { enqueueForString(result, callFactory) }, { result.invoke(HTTP_UNAUTHORIZED, null) })) {
                    recordSuccess(call, statusCode, body)
                    result.invoke(statusCode, body)
                } else Unit
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                recordFailure(t.message ?: "<NO MESSAGE>", t, call)
                result.invoke(0, null)
            }
        })
    }

    protected fun enqueueForStatus(result: (status: Int) -> Unit, callFactory: () -> Call<Void>) {
        val call = callFactory.invoke()
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val statusCode = response.code()
                if (authCheck(statusCode, { enqueueForStatus(result, callFactory) }, { result.invoke(HttpURLConnection.HTTP_UNAUTHORIZED) })) {
                    recordSuccess(call, statusCode, null)
                    result.invoke(statusCode)
                } else Unit
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                recordFailure(t.message ?: "<NO MESSAGE>", t, call)
                result.invoke(0)
            }
        })
    }

    protected open fun enqueueForInputStream(result: (status: Int, result: InputStream?) -> Unit, callFactory: () -> Call<ResponseBody>) {
        val call = callFactory.invoke()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val statusCode = response.code()
                var stream: InputStream? = null
                response.body().use { responseBody -> stream = responseBody?.byteStream() }
                if (authCheck(statusCode, { enqueueForInputStream(result, callFactory) }, { result.invoke(HttpURLConnection.HTTP_UNAUTHORIZED, null) })) {
                    recordSuccess(call, statusCode, null)
                    result.invoke(statusCode, stream)
                } else Unit
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                recordFailure(t.message ?: "<NO MESSAGE>", t, call)
                result.invoke(0, null)
            }
        })
    }

    protected open fun authCheck(statusCode: Int, retryCallback: () -> Unit?, failCallback: () -> Unit?): Boolean {
        return true
    }

// ////////////////////////////////////////////////////////////////////////////////
// Logging Methods
// ////////////////////////////////////////////////////////////////////////////////

    protected open fun recordFailure(errorText: String, t: Throwable? = null, call: Call<*>? = null) {
        WincdsClient.lastStatusCode = 0
        WincdsClient.lastResponse = ""
        WincdsClient.lastError = errorText
        WLogger.error("Operation failed: $errorText")
        if (t != null) {
            WLogger.error("EXCEPTION [${t.javaClass.simpleName}]: ${t.message}")
            WLogger.error("STACKTRACE: ${WLogger.stackTraceString(t)}")
        }
    }

    protected open fun recordSuccess(call: Call<*>, status: Int, body: Any? = null) {
        WincdsClient.lastRequest = "${call.request().method()}-${call.request().url()}"
        var requestBody = call.request().body() ?: ""
        if (requestBody != "") requestBody = "\nBODY: $requestBody"
        WincdsClient.lastStatusCode = status
        WincdsClient.lastResponse = Mapper.toJson(body)
        WincdsClient.lastError = ""
        WLogger.info("SUCCESS [$status] from [${WincdsClient.lastRequest}]: $body$requestBody")
    }
}
