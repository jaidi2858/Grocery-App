package com.junaid.groceryappassignment.utils.application


import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar




fun Context.color(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.color(colorCode: String): Int {
    return Color.parseColor(colorCode)
}


fun EditText.string(): String {
    return this.text.toString().trim()
}


fun View.visible() {
    this.visibility=View.VISIBLE
}
fun View.gone() {
    this.visibility=View.GONE
}


//====================== FRAGMENT BASE EXTENSIONS =====================


fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: String) {
    this?.view?.let {
        Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).show()
    } ?: showToast(message)

}






