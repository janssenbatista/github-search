package dev.janssenbatista.githubsearch.repositories

import android.content.Context
import dev.janssenbatista.githubsearch.R
import dev.janssenbatista.githubsearch.models.Repository
import dev.janssenbatista.githubsearch.services.ApiService
import retrofit2.Response

class ApiRepository(private val apiService: ApiService, private val context: Context) {

    suspend fun getAllRepositories(user: String): Result {
        return try {
            val response: Response<List<Repository>> = apiService.getAllRepositories(user)
            return when (response.code()) {
                200 -> Result.Success(response.body()!!)
                404 -> Result.Error(context.getString(R.string.user_not_found))
                else -> Result.Error(context.getString(R.string.server_error))
            }
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }


}

sealed class Result {
    object Loading : Result()
    class Success(val repositories: List<Repository>) : Result()
    class Error(val errorMessage: String) : Result()
}