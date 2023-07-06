package com.learning.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.learning.blogapp.core.Result
import com.learning.blogapp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeScreenViewModel(private val repo: HomeScreenRepo): ViewModel() {
    fun fetchLatestPosts() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess {postList->
            emit(postList)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }

    fun registerLikeButtonState(postId: String, liked: Boolean) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        kotlin.runCatching {
            repo.registerLikeButtonState(postId, liked)
        }.onSuccess {
            emit(Result.Success(Unit))
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }

}

class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}