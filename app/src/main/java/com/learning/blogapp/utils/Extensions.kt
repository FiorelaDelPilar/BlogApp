package com.learning.blogapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun View.hideKeayboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}