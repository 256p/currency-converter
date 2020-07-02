package com.example.currencyconverter.idlingResources

import androidx.test.espresso.IdlingResource
import com.revolut.rxdiffadapter.AsyncDiffRecyclerView

class RecyclerViewIdlingResource(
    private val recyclerView: AsyncDiffRecyclerView,
    private val position: Int
) : IdlingResource {
    lateinit var resourceCallback: IdlingResource.ResourceCallback
    var isIdle: Boolean = false

    override fun getName(): String = javaClass.name

    override fun isIdleNow(): Boolean {
        if (isIdle) return true
        if (recyclerView.adapter == null) return false
        isIdle = recyclerView.findViewHolderForLayoutPosition(position)?.itemView?.width ?: 0 > 0
        if (isIdle) resourceCallback.onTransitionToIdle()
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback!!
    }

}