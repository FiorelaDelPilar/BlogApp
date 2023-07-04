package com.learning.blogapp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.learning.blogapp.R
import com.learning.blogapp.data.remote.auth.AuthDataSource
import com.learning.blogapp.databinding.FragmentSetupProfileBinding
import com.learning.blogapp.domain.auth.AuthRepoImpl
import com.learning.blogapp.presentation.auth.AuthViewModel
import com.learning.blogapp.presentation.auth.AuthViewModelFactory

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {
    private lateinit var binding: FragmentSetupProfileBinding
    private val viewmodel by viewModels<AuthViewModel>{AuthViewModelFactory(AuthRepoImpl(AuthDataSource()))}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)

    }

}