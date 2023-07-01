package com.learning.blogapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.blogapp.core.Resource
import com.learning.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatestPosts():Resource<List<Post>>{
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()

        for(post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {
                postList.add(it) }
        }
        return Resource.Success(postList)
    }

}