package com.learning.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.learning.blogapp.R
import com.learning.blogapp.data.remote.auth.AuthDataSource
import com.learning.blogapp.databinding.FragmentLoginBinding
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

    private fun createUser(email: String, password: String, username: String) {
        viewmodel.signUp(email,password,username).observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Result.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is Result.Success ->{
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Result.Failure ->{
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(),"Error:${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun validateUserData(password: String, passwordConfirm: String, username: String, email: String): Boolean {
        if (!password.equals(passwordConfirm)) {
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