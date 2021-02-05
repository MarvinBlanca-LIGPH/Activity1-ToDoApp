package com.example.todoapp.util

import android.app.*
import android.view.View
import android.view.inputmethod.InputMethodManager

object HideKeyboardUtil {
    fun hideKeyboard(activity: Activity) {
        try {
            val inputManager: InputMethodManager = activity
                .getSystemService(Application.INPUT_METHOD_SERVICE) as InputMethodManager
            val currentFocusedView: View? = activity.currentFocus
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}