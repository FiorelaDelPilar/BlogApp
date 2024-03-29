package com.learning.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.learning.blogapp.R
import com.learning.blogapp.core.Result
import com.learning.blogapp.data.remote.auth.AuthDataSource
import com.learning.blogapp.databinding.FragmentLoginBinding
import com.learning.blogapp.domain.auth.AuthRepoImpl
import com.learning.blogapp.presentation.auth.AuthViewModel
import com.learning.blogapp.presentation.auth.AuthViewModelFactory
import com.learning.blogapp.utils.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding:FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    //private lateinit var firebaseAuth2: FirebaseAuth
    private val viewmodel by viewModels<AuthViewModel>{AuthViewModelFactory(AuthRepoImpl(
        AuthDataSource()
    ))}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        //firebaseAuth2 = FirebaseAuth.getInstance()
        isUserLoggedIn()
        doLogin()
        goToSignUpPage()
    }

    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {user->
            if(user.displayName.isNullOrEmpty()){
                findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
            }else{
                findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
            }
        }
    }

    private fun doLogin(){
        binding.btnSignin.setOnClickListener{
            it.hideKeayboard()
            val email = binding.editTextEmail.clean()
            val password = binding.editPassword.clean()
            validateCredentials(email,password)
            signIn(email,password)
        }
    }

    private fun goToSignUpPage(){
        binding.txtSignup.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    if(result.data?.displayName.isNullOrEmpty()){
                        findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
                    }else{
                        findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    }
                    Toast.makeText(
                        requireContext(),
                        "Welcome ${result.data?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
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