package com.learning.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.learning.blogapp.R
import com.learning.blogapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding:FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    //private lateinit var firebaseAuth2: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        //firebaseAuth2 = FirebaseAuth.getInstance()
        isUserLoggedIn()
        doLogin()
    }

    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogin(){
        binding.btnSignin.setOnClickListener{
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            validateCredentials(email,password)
            signIn(email,password)
        }
    }

    private fun validateCredentials(email:String, password:String){
        if(email.isEmpty()){
            binding.editTextEmail.error = "E-mail is empty"
            return
        }

        if(password.isEmpty()){
            binding.editPassword.error = "Password is empty"
            return
        }
    }

    private fun signIn(email:String, pasword:String){

    }
}