package com.example.tutorialdiwithkotlin.screens.common.mvcviews

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.example.tutorialdiwithkotlin.common.BaseObservable

/**
 * Created by khoado on 05,February,2020
 */

abstract class BaseViewMvc<ListenerType> : BaseObservable<ListenerType>(),
    ObservableViewMvc<ListenerType> {
    private lateinit var mRootView: View

    override fun getRootView(): View {
        return mRootView
    }


    protected fun setRootView(rootView: View) {
        mRootView = rootView
    }
    @Suppress("UNCHECKED_CAST")
    protected fun <T : View?> findViewById(@IdRes id: Int): T {
        return mRootView.findViewById<View>(id) as T
    }


    protected fun getContext(): Context {
        return getRootView().context
    }


    protected fun getString(@StringRes id: Int): String {
        return getContext().getString(id)
    }


    protected fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
        return getContext().getString(id, *formatArgs)
    }
}
