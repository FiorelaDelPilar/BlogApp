package com.learning.blogapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.common.math.BigIntegerMath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    /*private lateinit var  imageView: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1   */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}

//data class Ciudad(val population: Int=0, val color:String="", val pc:Int = 0, val imageUrl: String = "")


