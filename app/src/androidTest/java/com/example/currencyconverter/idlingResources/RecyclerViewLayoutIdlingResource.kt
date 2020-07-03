package com.example.currencyconverter.idlingResources

import android.view.ViewTreeObserver
import androidx.test.espresso.IdlingResource
import com.revolut.rxdiffadapter.AsyncDiffRecyclerView

class RecyclerViewLayoutIdlingResource(
    private val recyclerView: AsyncDiffRecyclerView,
    private val position: Int
) : IdlingResource {
    lateinit var resourceCallback: IdlingResource.ResourceCallback
    var isIdle: Boolean = false

    override fun getName(): String = javaClass.name

    override fun isIdleNow(): Boolean {
        if (isIdle) return true
        if (recyclerView.adapter == null) return false
        val viewHolderObserver =
            recyclerView.findViewHolderForLayoutPosition(position)?.itemView?.viewTreeObserver
        viewHolderObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                isIdle = true
                resourceCallback.onTransitionToIdle()
                viewHolderObserver.removeOnGlobalLayoutListener(this)
            }
        })
        return false
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback!!
    }

}