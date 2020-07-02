package com.example.currencyconverter

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.*


class MockApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()

        var mockData: String? = null

        if ("${BuildConfig.API_BASE_URL}latest\\?base=\\w{3}".toRegex().containsMatchIn(uri)) {
            when (uri.substring(uri.length - 3).toLowerCase()) {
                "eur" -> mockData = getJson("eur.json")
                "usd" -> mockData = getJson("usd.json")
            }
        }

        println("test")

        return if (mockData != null)
            chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(mockData)
                .body(mockData
                    .toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        else chain.proceed(chain.request())
    }

    private fun getJson(fileName: String): String {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val sc = Scanner(inputStream).useDelimiter("\\A")
        return if (sc.hasNext()) sc.next() else ""
    }

}