package com.learning.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.learning.blogapp.R
import com.learning.blogapp.data.model.Post
import com.learning.blogapp.databinding.FragmentHomeScreenBinding
import com.learning.blogapp.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

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

        binding.rvHome.adapter = HomeScreenAdapter(postList)

    }
}