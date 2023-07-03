package com.learning.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.resource.bitmap.BitmapImageDecoderResourceDecoder
import com.google.firebase.auth.FirebaseAuth
import com.learning.blogapp.R
import com.learning.blogapp.core.Resource
import com.learning.blogapp.data.remote.auth.LoginDataSource
import com.learning.blogapp.databinding.FragmentLoginBinding
import com.learning.blogapp.domain.auth.LoginRepoImpl
import com.learning.blogapp.presentation.auth.LoginScreenViewModel
import com.learning.blogapp.presentation.auth.LoginScreenViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding:FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    //private lateinit var firebaseAuth2: FirebaseAuth
    private val viewmodel by viewModels<LoginScreenViewModel>{LoginScreenViewModelFactory(LoginRepoImpl(
        LoginDataSource()
    ))}

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
        //se va al viewmodel ->repo-> implementación -> data source -> en este datasource se hace login con firebase
        viewmodel.signIn(email, pasword).observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    Toast.makeText(
                        requireContext(),
                        "Welcome ${result.data?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}