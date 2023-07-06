package com.learning.blogapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.learning.blogapp.R
import com.learning.blogapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val user = FirebaseAuth.getInstance().currentUser
        Glide.with(this).load(user?.photoUrl).centerCrop().into(binding.imgProfile)
        binding.txtProfileName.text = user?.displayName

    }
}