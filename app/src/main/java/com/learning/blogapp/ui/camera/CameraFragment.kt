package com.learning.blogapp.ui.camera

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.learning.blogapp.R
import com.learning.blogapp.data.remote.camera.CameraDataSource
import com.learning.blogapp.databinding.FragmentCameraBinding
import com.learning.blogapp.domain.camera.CameraRepoImpl
import com.learning.blogapp.presentation.camera.CameraViewModel
import com.learning.blogapp.presentation.camera.CameraViewModelFactory
import com.learning.blogapp.utils.*
import com.learning.blogapp.core.Result

class CameraFragment : Fragment(R.layout.fragment_camera) {
    private lateinit var binding: FragmentCameraBinding
    private var bitmap:Bitmap? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val viewModel by viewModels<CameraViewModel>{CameraViewModelFactory(CameraRepoImpl(CameraDataSource()))}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try{
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(requireContext(), "No se encontró app para tomar la foto.", Toast.LENGTH_SHORT).show()
        }

        binding.btnUploadPhoto.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploading photo...").create()
            bitmap?.let {
                viewModel.uploadPhoto(it, binding.photoDescription.clean()).observe(viewLifecycleOwner, Observer {result->
                    when(result){
                        is Result.Loading -> { alertDialog.show() }
                        is Result.Success -> {
                            alertDialog.dismiss()
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            alertDialog.dismiss()
                            Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgAddPhoto.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}