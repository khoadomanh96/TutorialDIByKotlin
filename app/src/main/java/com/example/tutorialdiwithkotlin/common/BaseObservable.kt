package com.example.tutorialdiwithkotlin.common

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by khoado on 05,February,2020
 */

abstract class BaseObservable<LISTENER_CLASS> {

    private val mListeners = Collections.newSetFromMap(ConcurrentHashMap<LISTENER_CLASS,Boolean>(1))

    open fun registerListener(listener : LISTENER_CLASS) {
        mListeners.add(listener)
    }
    open fun unregisterListener(listener: LISTENER_CLASS) {
        mListeners.remove(listener)
    }
    protected open fun getListeners() : Set<LISTENER_CLASS> {
        return Collections.unmodifiableSet(mListeners)
    }
}