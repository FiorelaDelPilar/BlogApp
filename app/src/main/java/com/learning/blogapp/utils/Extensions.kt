package com.learning.blogapp.utils

import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.clean():String = this.text.toString().trim()
fun EditText.clean():String = this.text.toString().trim()

fun TextInputEditText.errors() {
    if(this.clean().isEmpty()){
        this.error = "${this.hint.toString()} is empty"
        return@errors
    }
}

fun View.hide(){ this.visibility = View.GONE }

fun View.show(){ this.visibility = View.VISIBLE }