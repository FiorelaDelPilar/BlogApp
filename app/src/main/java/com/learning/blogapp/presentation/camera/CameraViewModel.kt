package com.learning.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import com.learning.blogapp.core.Result
import com.learning.blogapp.domain.camera.CameraRepo
import java.lang.Exception

class CameraViewModel(private val repo: CameraRepo): ViewModel() {
    fun uploadPhoto(imageBitmap: Bitmap, description:String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {

        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
}