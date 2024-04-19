package com.slowerror.rickandmorty.ui.base

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    protected fun showLongSnackBar(view: View, message: String?) {
        if (message.isNullOrEmpty()) return
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    protected fun showShortToast(context: Context, message: String?) {
        if (message.isNullOrEmpty()) return
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}