package com.learning.blogapp.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.learning.blogapp.core.Result
import com.learning.blogapp.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(private val repo: AuthRepo) : ViewModel(){
    fun signIn(email:String, password:String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.signIn(email,password)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun signUp(email:String, password:String, fullname:String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.signUp(email,password, fullname)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap: Bitmap, username: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateProfile(imageBitmap, username)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class AuthViewModelFactory(private val repo:AuthRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //return modelClass.getConstructor(LoginRepo::class.java).newInstance(repo)
        return AuthViewModel(repo) as T
    }
}