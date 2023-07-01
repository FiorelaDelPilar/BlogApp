package com.learning.blogapp.domain

import com.learning.blogapp.core.Resource
import com.learning.blogapp.data.model.Post

interface HomeScreenRepo {
    //Se detallan los métodos que se van a usar para hacer operaciones en el servidor
    suspend fun getLatestPosts():Resource<List<Post>> //lo que retorna (luego de los :)
}