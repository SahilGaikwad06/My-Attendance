package com.valisha.myattendance.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer

class LoggingInterceptor : Interceptor {

    private val TAG = LoggingInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        Log.e(TAG,"url: $url")
        /*------------- REQUEST ------------*/
        val token = chain.request().header("token")
        val buffer = Buffer()
        chain.request().body?.writeTo(buffer)
        Log.e(TAG,"intercept: request: ${buffer.readUtf8()}\nis token null or empty: ${token.isNullOrEmpty()}")

        /*------------- RESPONSE ------------*/
        buffer.clear()
        var response = chain.proceed(chain.request())
        Log.e(TAG,"response code: ${response.code}")
        val body = response.body
        if(body!=null){
            val bytes = body.bytes()
            buffer.write(bytes)
            Log.e(TAG,"intercept: response: ${buffer.readUtf8()}")
            val newBody = ResponseBody.create("application/json".toMediaTypeOrNull(),bytes)
            response = response.newBuilder().body(newBody)
                    .build()
        }
        return response
    }
}