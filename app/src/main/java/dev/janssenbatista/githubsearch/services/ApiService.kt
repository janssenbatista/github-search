package dev.janssenbatista.githubsearch.services

import dev.janssenbatista.githubsearch.models.Repository
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/users/{user}/repos?visibility=public")
    suspend fun getAllRepositories(@Path("user") user: String): Response<List<Repository>>

    companion object {

        private const val BASE_URL = "https://api.github.com"

        fun getInstance(): ApiService {

            val client = OkHttpClient.Builder()
                .addInterceptor(LogInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
                .create(ApiService::class.java)
        }
    }
}