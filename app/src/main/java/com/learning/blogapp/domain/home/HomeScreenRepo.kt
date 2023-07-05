package com.learning.blogapp.domain.home

import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {
    //Se detallan los métodos que se van a usar para hacer operaciones en el servidor
    suspend fun getLatestPosts(): Flow<Result<List<Post>>> //lo que retorna (luego de los :)
}