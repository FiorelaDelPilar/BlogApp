package com.learning.blogapp.domain.home

import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post

interface HomeScreenRepo {
    //Se detallan los métodos que se van a usar para hacer operaciones en el servidor
    suspend fun getLatestPosts(): Result<List<Post>> //lo que retorna (luego de los :)
}