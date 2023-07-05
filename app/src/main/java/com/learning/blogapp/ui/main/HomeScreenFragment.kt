package com.learning.blogapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.learning.blogapp.R
import com.learning.blogapp.core.Result
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.data.remote.home.HomeScreenDataSource
import com.learning.blogapp.databinding.FragmentHomeScreenBinding
import com.learning.blogapp.domain.home.HomeScreenRepoImpl
import com.learning.blogapp.presentation.HomeScreenViewModel
import com.learning.blogapp.presentation.HomeScreenViewModelFactory
import com.learning.blogapp.ui.main.adapters.HomeScreenAdapter
import com.learning.blogapp.ui.main.adapters.OnPostClickListener
import com.learning.blogapp.utils.*


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), OnPostClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel>{
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
            HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

      /*
        val postList = listOf(
            Post(
                "https://i.pinimg.com/236x/85/99/ea/8599ea1d4454394617df83d48cb0f218.jpg",
                "Tara Grem",
                Timestamp.now(),
                "https://i.pinimg.com/236x/39/62/54/396254cab60fe9c8313ad6195ab861f2.jpg"
            ),
            Post(
                "https://i.pinimg.com/236x/f4/6d/39/f46d39518ca726b2a177614e2b3ca3a1.jpg",
                "Ethan Trest",
                Timestamp.now(),
                "https://i.pinimg.com/236x/90/5a/55/905a5534acc69debf3f4d5ec5de5ab92.jpg"
            ),
            Post(
                "https://i.pinimg.com/236x/4b/56/e0/4b56e05a9d97331c7cc26716f0a0ffdd.jpg",
                "Ian Trest",
                Timestamp.now(),
                "https://i.pinimg.com/236x/58/9d/90/589d902a331fa4c6bf3fe9d63c690f61.jpg"
            )
        )
       */
        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.show()
                }

                is Result.Success ->{
                    binding.progressBar.hide()
                    if(result.data.isEmpty()){
                        binding.emptyContainer.show()
                        return@Observer
                    }else{
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data, this)
                }

                is Result.Failure ->{
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Ocurrió un error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }

            }
        })

        //binding.rvHome.adapter = HomeScreenAdapter(postList)

    }

    override fun onLikeButtonClick(post: Post, liked: Boolean) {
    }
}