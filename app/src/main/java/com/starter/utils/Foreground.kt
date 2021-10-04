package com.starter.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Developer: Mohammad Zaki
 */
/**
 * Usage:
 *
 *
 * A. Create Application class
 * B. Insert Foreground.init(this); to onCreate() of Application class
 * C. call Foreground.get().isForeground() where you want.
 *
 *
 * 1. Get the Foreground Singleton, passing a Context or Application object unless you
 * are sure that the Singleton has definitely already been initialised elsewhere.
 *
 *
 * 2.a) Perform a direct, synchronous check: Foreground.isForeground() / .isBackground()
 *
 *
 * or
 *
 *
 * 2.b) Register to be notified (useful in Service or other non-UI components):
 *
 *
 * Foreground.Listener myListener = new Foreground.Listener(){
 * public void onBecameForeground(){
 * // ... whatever you want to do
 * }
 * public void onBecameBackground(){
 * // ... whatever you want to do
 * }
 * }
 *
 *
 * public void onCreate(){
 * super.onCreate();
 * Foreground.get(this).addListener(listener);
 * }
 *
 *
 * public void onDestroy(){
 * super.onCreate();
 * Foreground.get(this).removeListener(listener);
 * }
 */
class Foreground : Application.ActivityLifecycleCallbacks {
    /**
     * Is foreground boolean.
     *
     * @return the boolean
     */
    var isForeground = false
        private set
    private var paused = true
    private val handler = Handler()
    private val listeners: MutableList<Listener> =
        CopyOnWriteArrayList()
    private var check: Runnable? = null

    /**
     * Is background boolean.
     *
     * @return the boolean
     */
    val isBackground: Boolean
        get() = !isForeground

    /**
     * Add listener.
     *
     * @param listener the listener
     */
    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    /**
     * Remove listener.
     *
     * @param listener the listener
     */
    fun removeListener(listener: Listener?) {
        listeners.remove(listener)
    }

    override fun onActivityResumed(activity: Activity) {
        paused = false
        val wasBackground = !isForeground
        isForeground = true
        if (check != null) {
            handler.removeCallbacks(check!!)
        }
        if (wasBackground) {
            Log.i(TAG, "went foreground")
            for (l in listeners) {
                l.onBecameForeground()
            }
        } else {
            Log.i(TAG, "still foreground")
        }
    }

    override fun onActivityPaused(activity: Activity) {
        paused = true
        if (check != null) {
            handler.removeCallbacks(check!!)
        }
        check = object : Runnable {
            override fun run() {
                if (isForeground && paused) {
                    isForeground = false
                    Log.i(TAG, "went background")
                    for (l in listeners) {
                        l.onBecameBackground()
                    }
                } else {
                    Log.i(TAG, "still foreground")
                }
            }
        }
        handler.postDelayed(check!!, CHECK_DELAY)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}


    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    /**
     * The interface Listener.
     */
    interface Listener {
        /**
         * On became foreground.
         */
        fun onBecameForeground()

        /**
         * On became background.
         */
        fun onBecameBackground()
    }

    companion object {
        private const val CHECK_DELAY: Long = 500
        private val TAG = Foreground::class.java.name
        private var instance: Foreground? = null
        /**
         * Its not strictly necessary to use this method - _usually_ invoking
         * get with a Context gives us a path to retrieve the Application and
         * initialise, but sometimes (e.g. in test harness) the ApplicationContext
         * is != the Application, and the docs make no guarantees.
         *
         * @param application Instance of application class of app
         * @return an initialised Foreground instance
         */
        fun init(application: Application): Foreground? {
            if (instance == null) {
                instance = Foreground()
                application.registerActivityLifecycleCallbacks(instance)
            }
            return instance
        }

        /**
         * Get foreground.
         *
         * @param application the application
         * @return the foreground
         */
        operator fun get(application: Application): Foreground? {
            if (instance == null) {
                init(application)
            }
            return instance
        }

        /**
         * Get foreground.
         *
         * @param ctx the ctx
         * @return the foreground
         */
        operator fun get(ctx: Context): Foreground? {
            if (instance == null) {
                val appCtx = ctx.applicationContext
                if (appCtx is Application) {
                    return init(appCtx)
                }
                throw IllegalStateException("Foreground is not initialised and cannot obtain the Application object")
            }
            return instance
        }

        /**
         * Get foreground.
         *
         * @return the foreground
         */
        fun get(): Foreground? {
            checkNotNull(instance) { "Foreground is not initialised - invoke at least once with parameterised init/get" }
            return instance
        }
    }
}