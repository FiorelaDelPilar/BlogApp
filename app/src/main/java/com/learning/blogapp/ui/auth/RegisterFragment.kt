package com.learning.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.blogapp.R
import com.learning.blogapp.databinding.FragmentLoginBinding
import com.learning.blogapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener{
            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val passwordConfirm = binding.editTextConfirmPassword.text.toString().trim()

            if(!password.equals(passwordConfirm)){
                binding.editTextPassword.error = "Password does not match"
                binding.editTextConfirmPassword.error = "Password does not match"
                return@setOnClickListener
            }

            if(username.isEmpty()){
                binding.editTextUsername.error = "Username is empty"
                return@setOnClickListener
            }

            if(email.isEmpty()){
                binding.editTextEmail.error = "E-mail is empty"
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.editTextPassword.error = "Password is empty"
                return@setOnClickListener
            }

            if(passwordConfirm.isEmpty()){
                binding.editTextConfirmPassword.error = "Password is empty"
                return@setOnClickListener
            }

            Log.d("signUpData", "data: $username $email $password $passwordConfirm")
        }
    }

}