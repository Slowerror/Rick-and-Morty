package com.slowerror.rickandmorty.ui.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    fun showLongSnackBar(view: View, message: String?) {
        if (message.isNullOrEmpty()) return
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}