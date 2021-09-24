package com.starter.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.starter.R
import com.starter.utils.extensions.SafeString.assign


open class AlertDialog {
    private var alertDialog: Dialog? = null
    /**
     * The instance of the Activity on which the
     * AlertDialog will be displayed
     */
    private var activity: Activity? = null
    /**
     * The receiver to which the AlertDialog
     * will return the CallBacks
     *
     *
     * Note: listener should be an instance of
     * AlertDialog.Listener
     */
    private var listener: Listener? = null
    /**
     * The code to differentiate the various CallBacks
     * from different Dialogs
     */
    private var purpose = 0
    /**
     * The title to be set on the Dialog
     */
    private var title: String? = null
    /**
     * The message to be set on the Dialog
     */
    private var message: String? = null
    /**
     * The text to be set on the Action Button
     */
    private var actionButton: String? = null
    private var isClickable = false
    /**
     * The data to sent via the Dialog from the
     * remote parts of the Activity to other
     * parts
     */
    private var backpack: Bundle? = null
    /**
     * Animation Name
     */
    private var animName: String? = null

    /**
     * Method to create and display the alert alertDialog
     *
     * @return
     */
    private fun init(): AlertDialog {
        try {
            alertDialog = Dialog(activity!!,android.R.style.Theme_Translucent_NoTitleBar)
            alertDialog!!.setContentView(R.layout.dialog_alert)
            val dialogWindow = alertDialog!!.window
            val layoutParams = dialogWindow!!.attributes
            layoutParams.dimAmount = 0.8f
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            alertDialog!!.setCancelable(false)
            alertDialog!!.setCanceledOnTouchOutside(false)
            val tvMessage = alertDialog!!.findViewById<TextView>(R.id.tvMessage)
            tvMessage.movementMethod = ScrollingMovementMethod.getInstance()
            val btnAction =
                alertDialog!!.findViewById<Button>(R.id.btnPositive)
            val loading: LottieAnimationView = alertDialog!!.findViewById(R.id.loading)
            loading.setAnimation(animName)
            tvMessage.text = Html.fromHtml(message)
            btnAction.text = actionButton
            btnAction.setOnClickListener {
                if (isClickable) {
                    alertDialog!!.dismiss()
                }
                if (listener != null) listener!!.performPostAlertAction(purpose, backpack)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    /**
     * Method to init the initialized alertDialog
     */
    fun show(): Dialog? { // Check if activity lives
        if (activity != null) // Check if alertDialog contains data
            if (alertDialog != null) {
                try { // Show the Dialog
                    alertDialog!!.show()
                    return alertDialog
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        return alertDialog
    }

    /**
     * @return return alert dialog status
     */
    val isShowing: Boolean
        get() = if (alertDialog == null) false else alertDialog!!.isShowing

    /**
     * Method to dismiss the AlertDialog, if possible
     */
    fun dismiss() { // Check if activity lives
        if (activity != null) // Check if the Dialog is null
            if (alertDialog != null) // Check whether the alertDialog is visible
                if (alertDialog!!.isShowing) {
                    try { // Dismiss the Dialog
                        alertDialog!!.dismiss()
                        alertDialog = null
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
    }

    /**
     * Interfaces the events from the AlertDialog
     * to the Calling Context
     */
    interface Listener {
        /**
         * Override this method to listen to
         * the events fired from AlertDialog
         *
         * @param purpose
         * @param backpack
         */
        fun performPostAlertAction(purpose: Int, backpack: Bundle?)
    }

    interface OnActionPerformed {
        fun positive()
    }

    /**
     * Class to help building the Alert Dialog
     */
    class Builder : AlertDialog {
        private val alertDialog =
            AlertDialog()

        /**
         * Constructor to build a alertDialog for Activity
         *
         * @param activity
         */
        constructor(activity: Activity?) {
            alertDialog.activity = activity
            if (activity is Listener) alertDialog.listener =
                activity
        }

        /**
         * Constructor to build a alertDialog for Fragment
         *
         * @param fragment
         */
        constructor(fragment: Fragment) {
            alertDialog.activity = fragment.activity
            if (fragment is Listener) alertDialog.listener =
                fragment
        }

        /**
         * Sets the a unique purpose code to differentiate
         * between the CallBacks
         *
         * @param purpose
         * @return
         */
        fun purpose(purpose: Int): Builder {
            alertDialog.purpose = purpose
            return this
        }

        /**
         * Sets the a custom listener to receive the CallBacks
         *
         * @param listener
         * @return
         */
        fun listener(listener: Listener?): Builder {
            alertDialog.listener = listener
            return this
        }

        /**
         * Set the data to be sent via callback
         *
         * @param backpack
         * @return
         */
        fun backpack(backpack: Bundle?): Builder {
            alertDialog.backpack = backpack
            return this
        }

        /**
         * Set the message for the AlertDialog
         *
         * @param resourceId
         * @return
         */
        fun title(resourceId: Int): Builder {
            return title(alertDialog.activity!!.getString(resourceId))
        }

        /**
         * Pass Animation Name to Play
         *
         * @param animName
         * @return
         */
        fun animName(animName: String?): Builder {
            alertDialog.animName = animName
            return this
        }

        fun buttonClickable(isClickable: Boolean): Builder {
            alertDialog.isClickable = isClickable
            return this
        }

        /**
         * Set the message for the AlertDialog
         *
         * @param title
         * @return
         */
        fun title(title: String?): Builder {
            alertDialog.title = title
            return this
        }

        /**
         * Set the message for the AlertDialog
         *
         * @param resourceId
         * @return
         */
        fun message(resourceId: Int): Builder {
            return message(alertDialog.activity!!.getString(resourceId))
        }

        /**
         * Set the message for the AlertDialog
         *
         * @param message
         * @return
         */
        fun message(message: String?): Builder {
            var message = message
            if (message == null) message =
                alertDialog.activity!!.getString(R.string.unexpected_error_occurred)
            alertDialog.message = message
            return this
        }

        /**
         * Set the actionButton for the AlertDialog
         *
         * @param resourceId
         * @return
         */
        fun button(resourceId: Int): Builder {
            return button(alertDialog.activity!!.getString(resourceId))
        }

        /**
         * Set the actionButton for the AlertDialog
         *
         * @param button
         * @return
         */
        fun button(button: String?): Builder {
            alertDialog.actionButton = button
            return this
        }

        /**
         * Method to build an AlertDialog and display
         * it on the screen. The instance returned can
         * be used to manipulate the alertDialog in future.
         *
         * @return
         */
        fun build(): AlertDialog {
            alertDialog.message =
                assign(alertDialog.message, getString(R.string.message))
            alertDialog.actionButton =
                assign(alertDialog.actionButton, getString(R.string.ok_text))
            return alertDialog.init()
        }

        /**
         * Method to retrieve a String Resource
         *
         * @param resourceId
         * @return
         */
        private fun getString(resourceId: Int): String {
            return alertDialog.activity!!.getString(resourceId)
        }
    }

    companion object {
        private val TAG =
            AlertDialog::class.java.simpleName
    }
}