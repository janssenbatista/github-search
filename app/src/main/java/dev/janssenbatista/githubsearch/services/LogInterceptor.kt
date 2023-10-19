package dev.janssenbatista.githubsearch.services

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.i(TAG, response.code().toString())
        return response
    }

    companion object {
        const val TAG = "Interceptor"
    }
}