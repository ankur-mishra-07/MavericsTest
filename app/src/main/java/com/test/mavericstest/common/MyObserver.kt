package com.test.mavericstest.common

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Demonstrates usage of basic LifecycleObserver.
 */
class MyObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(
            LOG_TAG,
            "resumed observing lifecycle."
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(
            LOG_TAG,
            "paused observing lifecycle."
        )
    }

    companion object {
        private val LOG_TAG =
            MyObserver::class.java.simpleName
    }
}