package uz.project.minusgram.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

fun Fragment.showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun Fragment.showHideView(view: View, isActive: Boolean) {
    view.isVisible = isActive
}
