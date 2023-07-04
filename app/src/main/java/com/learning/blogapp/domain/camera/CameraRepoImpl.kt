package com.learning.blogapp.domain.camera

import android.graphics.Bitmap
import com.learning.blogapp.data.remote.camera.CameraDataSource

class CameraRepoImpl(private val dataSoure:CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {

    }
}