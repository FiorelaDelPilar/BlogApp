package com.learning.blogapp.data.remote.home

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatestPosts(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        //val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").orderBy("created_at", Query.Direction.DESCENDING).get().await()

        for(post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {
                it.apply { created_at = post.getTimestamp("created_at", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)?.toDate() }
                postList.add(it) }
        }

        //postList.sortByDescending { it.created_at } -> no es óptimo
        return Result.Success(postList)
    }

}