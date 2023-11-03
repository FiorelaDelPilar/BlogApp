package com.learning.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {
    fun fetchLatestPosts() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess { postList ->
            emit(postList)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }

    fun registerLikeButtonState(postId: String, liked: Boolean) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            kotlin.runCatching {
                repo.registerLikeButtonState(postId, liked)
            }.onSuccess {
                emit(Result.Success(Unit))
            }.onFailure {
                emit(Result.Failure(Exception(it.message)))
            }
        }

    val latestPosts: StateFlow<Result<List<Post>>> = flow {
        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess { resultPostList ->
            emit(resultPostList)
        }.onFailure { throwable ->
            emit(Result.Failure(Exception(throwable)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )

    private val posts = MutableStateFlow<Result<List<Post>>>(Result.Loading())
    fun fetchPosts() = viewModelScope.launch {
        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess { resultPostList ->
            posts.value = resultPostList
        }.onFailure { throwable ->
            posts.value = Result.Failure(Exception(throwable))
        }
    }

    fun getPosts(): StateFlow<Result<List<Post>>> = posts
}

class HomeScreenViewModelFactory(private val repo: HomeScreenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}