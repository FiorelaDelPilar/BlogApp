package com.learning.blogapp.data.remote.home

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatestPosts(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()

        for(post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {
                postList.add(it) }
        }
        return Result.Success(postList)
    }

}