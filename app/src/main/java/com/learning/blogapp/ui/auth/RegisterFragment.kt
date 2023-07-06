package com.learning.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.learning.blogapp.R
import com.learning.blogapp.data.remote.auth.AuthDataSource
import com.learning.blogapp.databinding.FragmentRegisterBinding
import com.learning.blogapp.domain.auth.AuthRepoImpl
import com.learning.blogapp.presentation.auth.AuthViewModel
import com.learning.blogapp.presentation.auth.AuthViewModelFactory
import com.learning.blogapp.core.Result
import com.learning.blogapp.utils.*

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewmodel by viewModels<AuthViewModel>{ AuthViewModelFactory(AuthRepoImpl(AuthDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener{
            val username = binding.editTextUsername.clean()
            val email = binding.editTextEmail.clean()
            val password = binding.editTextPassword.clean()
            val passwordConfirm = binding.editTextConfirmPassword.clean()


            /*binding.editTextUsername.errors()
            binding.editTextEmail.errors()
            binding.editTextPassword.errors()
            binding.editTextConfirmPassword.errors()*/

            if (validateUserData(password, passwordConfirm, username, email)) return@setOnClickListener

            //Log.d("signUpData", "data: $username $email $password $passwordConfirm")
            createUser(email, password, username)
        }
    }

    private fun createUser(email: String, password: String, fullname: String) {
        viewmodel.signUp(email,password,fullname).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignup.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error:${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun validateUserData(password: String, passwordConfirm: String, username: String, email: String): Boolean {
        if (password != passwordConfirm) {
            binding.editTextPassword.error = "Password does not match"
            binding.editTextConfirmPassword.error = "Password does not match"
            return true
        }

        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "E-mail is empty"
            return true
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }

        if (passwordConfirm.isEmpty()) {
            binding.editTextConfirmPassword.error = "Password is empty"
            return true
        }
        return false
    }

}