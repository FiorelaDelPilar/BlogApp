package com.learning.blogapp.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.blogapp.R
import com.learning.blogapp.core.BaseViewHolder
import com.learning.blogapp.core.TimeUtils
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.databinding.PostItemViewBinding
import com.learning.blogapp.utils.*

class HomeScreenAdapter(private val postList: List<Post>, private val onPostClickListener: OnPostClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private var postClickListener: OnPostClickListener? = null

    init{
        postClickListener = onPostClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            setupProfileInfo(item)
            addPostTimeStamp(item)
            setupPostImage(item)
            setupPostDescription(item)
            tintHeartIcon(item)
            setupLikeCount(item)
            setLikeClickAction(item)
        }

        private fun setupProfileInfo(post: Post) {
            Glide.with(context).load(post.poster?.profile_picture).centerCrop()
                .into(binding.profilePicture)
            binding.profileName.text = post.poster?.username
        }

        private fun addPostTimeStamp(post: Post) {
            val createdAt = post.created_at?.time?.div(1000L)?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }

        private fun setupPostImage(post: Post) {
            Glide.with(context).load(post.post_image).centerCrop().into(binding.postImage)
        }

        private fun setupPostDescription(post: Post) {
            if (post.post_description.isEmpty()) {
                binding.postDescription.hide()
            } else {
                binding.postDescription.show()
                binding.postDescription.text = post.post_description
            }
        }

        private fun tintHeartIcon(post: Post) {
            if (!post.liked) {
                binding.btnLike.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.baseline_favorite_border_24
                    )
                )
                binding.btnLike.setColorFilter(ContextCompat.getColor(context, R.color.blue))
            } else {
                binding.btnLike.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.baseline_favorite_24
                    )
                )
                binding.btnLike.setColorFilter(ContextCompat.getColor(context, R.color.blue))
            }
        }

        private fun setupLikeCount(post: Post) {
            if(post.likes > 0){
                binding.likeCount.show()
                binding.likeCount.text ="${post.likes} likes"
            }else{
                binding.likeCount.hide()
            }
        }

        private fun setLikeClickAction(post: Post){
            binding.btnLike.setOnClickListener {
                if(post.liked) post.apply { liked = false } else post.apply { liked = true }
                tintHeartIcon(post)
                postClickListener?.onLikeButtonClick(post, post.liked)
            }
        }
    }
}

interface OnPostClickListener {
    fun onLikeButtonClick(post:Post, liked:Boolean)
}