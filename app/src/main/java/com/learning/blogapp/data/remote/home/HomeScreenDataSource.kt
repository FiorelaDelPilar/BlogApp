package com.learning.blogapp.data.remote.home

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeScreenDataSource {
    suspend fun getLatestPosts(): Flow<Result<List<Post>>> = callbackFlow {
        val postList = mutableListOf<Post>()

        var postReference: Query? = null

        try {
            postReference = FirebaseFirestore.getInstance().collection("posts")
                .orderBy("created_at", Query.Direction.DESCENDING)
        } catch (e: Throwable) {
            close(e)
        }

        val suscription = postReference?.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener
            try {
                postList.clear()
                for (post in value.documents) {
                    post.toObject(Post::class.java)?.let {
                        it.apply {
                            created_at = post.getTimestamp(
                                "created_at",
                                DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                            )?.toDate()
                        }
                        postList.add(it)
                    }
                }
            } catch (e: Exception) {
                close(e)
            }
            offer(Result.Success(postList))
        }
        awaitClose { suscription?.remove() }
    }
}