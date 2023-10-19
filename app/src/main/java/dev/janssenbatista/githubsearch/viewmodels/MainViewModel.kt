package dev.janssenbatista.githubsearch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.janssenbatista.githubsearch.App
import dev.janssenbatista.githubsearch.repositories.ApiRepository
import dev.janssenbatista.githubsearch.repositories.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result

    fun getAllRepositories(user: String) {
        _result.value = Result.Loading
        viewModelScope.launch {
            _result.value = repository.getAllRepositories(user)
        }
    }


    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val repository = (application as App).repository
                return MainViewModel(repository = repository) as T
            }
        }
    }
}