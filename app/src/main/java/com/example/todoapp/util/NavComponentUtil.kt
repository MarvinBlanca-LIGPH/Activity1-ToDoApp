package com.example.todoapp.util

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object NavComponentUtil {
    fun Fragment.navigate(@IdRes id : Int, vararg args: Pair<String, Any?>): Boolean {
        val bundle = bundleOf(*args)
        findNavController().navigate(id, bundle)
        return true
    }
}