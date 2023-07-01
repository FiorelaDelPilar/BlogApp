package com.learning.blogapp.domain

import com.learning.blogapp.core.Resource
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.data.remote.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSoure: HomeScreenDataSource) : HomeScreenRepo{
    //Encargado de conectar el datasource que es de donde vienen los datos para retornar ese valor
    override suspend fun getLatestPosts(): Resource<List<Post>>  = dataSoure.getLatestPosts()
}