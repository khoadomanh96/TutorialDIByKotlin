package com.example.tutorialdiwithkotlin.screens.common.mvcviews

/**
 * Created by khoado on 05,February,2020
 */

interface ObservableViewMvc<ListenerType> : ViewMvc {

    fun registerListener(listener : ListenerType)

    fun unregisterListener(listener: ListenerType)
}