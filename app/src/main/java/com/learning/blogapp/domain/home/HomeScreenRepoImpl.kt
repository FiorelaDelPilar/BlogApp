package com.learning.blogapp.domain.home

import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSoure: HomeScreenDataSource) : HomeScreenRepo {
    //Encargado de conectar el datasource que es de donde vienen los datos para retornar ese valor
    override suspend fun getLatestPosts(): Result<List<Post>> = dataSoure.getLatestPosts()
}