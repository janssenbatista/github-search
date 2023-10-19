package dev.janssenbatista.githubsearch

import android.app.Application
import dev.janssenbatista.githubsearch.repositories.ApiRepository
import dev.janssenbatista.githubsearch.services.ApiService

class App : Application() {

    val repository by lazy {
        ApiRepository(
            ApiService.getInstance(), applicationContext
        )
    }
}