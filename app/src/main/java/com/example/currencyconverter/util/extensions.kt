package com.example.currencyconverter.util

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.revolut.rxdiffadapter.AsyncDiffRecyclerView
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

inline fun <T : View> T.waitForLayout(crossinline f: (view: T) -> Unit) =
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f(this@waitForLayout)
            }
        }
    })

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProvider(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProvider(this, provider).get(VM::class.java)

fun Float.round(decPlace: Int): Float = (kotlin.math.round(this * 10.0.pow(decPlace)) / 10f.pow(decPlace)).toFloat()