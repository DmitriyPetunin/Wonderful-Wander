package com.example.network.interceptor

import com.example.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        val newUrl = req.url.newBuilder()
            .addQueryParameter("apikey", BuildConfig.GEO_CODER_API)
            .addQueryParameter("format","json")
            .build()

        val newReq = req.newBuilder().url(newUrl).build()

        return chain.proceed(newReq)
    }
}