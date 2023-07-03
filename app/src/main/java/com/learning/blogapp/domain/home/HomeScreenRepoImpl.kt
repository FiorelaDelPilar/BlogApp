package com.learning.blogapp.domain.home

import com.learning.blogapp.core.Resource
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSoure: HomeScreenDataSource) : HomeScreenRepo {
    //Encargado de conectar el datasource que es de donde vienen los datos para retornar ese valor
    override suspend fun getLatestPosts(): Resource<List<Post>>  = dataSoure.getLatestPosts()
}