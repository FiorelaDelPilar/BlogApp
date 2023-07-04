package com.learning.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.learning.blogapp.databinding.ActivityMainBinding
import com.learning.blogapp.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController:NavController

    /*private lateinit var  imageView: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1   */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //estos val es una forma de solucionar error común por el navhost pero también se podría usar en el xml "fragment" en lugar de "FragmentContainerView" y abajo setupWithNavController(findNavController(R.id.nav_host_fragment))
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        observeDestinationChange()

        /*val btnTakePicture = findViewById<Button>(R.id.btn_take_picture)
        imageView = findViewById(R.id.imageView)

        btnTakePicture.setOnClickListener{
            throw RuntimeException("Crashlytics test")
            dispatchTakePictureIntent()
        }*/

        /*
            //Consular información
        val db = FirebaseFirestore.getInstance()
        //Una vez
        db.collection("ciudades").document("NY").get().addOnSuccessListener {document ->
            document?.let {
                /*
                Log.d("Firebase","DocumentSnapshot data: ${document.data}")
                val color = document.getString("color")
                val population = document.getLong("population")
                Log.d("Firebase", "Population: $population")
                Log.d("Firebase", "Color: $color")
                */
                val ciudad = document.toObject(Ciudad::class.java)
                Log.d("Firebase", "Color:${ciudad?.color}")
                Log.d("Firebase", "Population:${ciudad?.population}")
                Log.d("Firebase", "Postal code:${ciudad?.pc}")
            }
        }.addOnFailureListener{error ->
            Log.e("FirebaseError", error.toString())
        }

        //En vivo
        db.collection("ciudades").document("NY").addSnapshotListener{value, error->
            value?.let {document->
                val ciudad = document.toObject(Ciudad::class.java)
                Log.d("Firebase", "Color:${ciudad?.color}")
                Log.d("Firebase", "Population:${ciudad?.population}")
                Log.d("Firebase", "Postal code:${ciudad?.pc}")
            }
        }

        //Ingresar información
        db.collection("ciudades").document("LA").set(Ciudad(300, "Red")).addOnSuccessListener {
            Log.d("Firebase", "Se guardó la ciudad correctamente")
        }.addOnFailureListener{error->
            Log.e("FirebaseError", error.toString())
        }
         */
    }
    
    /*private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try{
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(this, "No se encontró app para tomar la foto.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            uploadPicture(imageBitmap)
        }
    }

    private fun uploadPicture(bitmap: Bitmap){
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("imagenes/${UUID.randomUUID()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imagesRef.putBytes(data)

        uploadTask.continueWithTask { task->
            if(!task.isSuccessful){
                task.exception?.let{exception->
                    throw exception
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener{task->
            if(task.isSuccessful){
                val downloadUrl = task.result.toString()
                FirebaseFirestore.getInstance().collection("ciudades").document("LA").update(mapOf("imageUrl" to downloadUrl))
                Log.d("Storage","uploadPictureURL: $downloadUrl")
            }
        }
    }*/

    private fun observeDestinationChange(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.loginFragment ->{
                    binding.bottomNavigationView.hide()
                }
                R.id.registerFragment ->{
                    binding.bottomNavigationView.hide()
                }
                R.id.setupProfileFragment ->{
                    binding.bottomNavigationView.hide()
                }
                else->{
                    binding.bottomNavigationView.show()
                }
            }
        }
    }
}


//data class Ciudad(val population: Int=0, val color:String="", val pc:Int = 0, val imageUrl: String = "")


