package com.wincdspro.app.test

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.URI

class FakeNetworkInterceptor : Interceptor {
    companion object {
        var responses: MutableMap<String, String> = mutableMapOf()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri: URI = chain.request().url().uri()
        val path: String = uri.path

        return if (responses[path] != null) {
            val responseString = responses[path] ?: ""

            Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            chain.proceed(chain.request())
        }
    }
}
