package com.starter.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.starter.R

/**
 * Class custom Alert Dialog
 */
class CustomAlertDialog
/**
 * Instantiates a new Custom alert dialog.
 *
 * @param builder the builder
 */(builder: Builder?) {
    /**
     * The interface Custom dialog interface.
     */
    interface CustomDialogInterface {
        /**
         * The interface On click listener.
         */
        interface OnClickListener {
            /**
             * On click.
             */
            fun onClick()
        }

        /**
         * The interface On dismiss listener.
         */
        interface OnDismissListener {
            /**
             * On dismiss.
             */
            fun onDismiss()
        }

        /**
         * The interface On cancel listener.
         */
        interface OnCancelListener {
            /**
             * On cancel.
             */
            fun onCancel()
        }
    }

    /**
     * The type Builder.
     */
    class Builder(private val mContext: Context) {
        private val mInflater: LayoutInflater
        private var mTitle: CharSequence? = null
        private var mMessage: CharSequence? = null
        private var mOptional: CharSequence? = null
        private var mImage: CharSequence? = null
        private var mPositiveButtonText: CharSequence? = null
        private var mPositiveButtonListener: CustomDialogInterface.OnClickListener? =
            null
        private var mNegativeButtonText: CharSequence? = null
        private var mNegativeButtonListener: CustomDialogInterface.OnClickListener? =
            null
        private var mCancelable = true
        private var mOnCancelListener: CustomDialogInterface.OnCancelListener? =
            null
        private var mOnDismissListener: CustomDialogInterface.OnDismissListener? =
            null
        private var mBuilder: AlertDialog.Builder? = null
        private var mAlertDialog: AlertDialog? = null
        /**
         * Set the title using the given resource id.
         *
         * @param titleId the title id
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setTitle(@StringRes titleId: Int): Builder {
            mTitle = mContext.getText(titleId)
            return this
        }

        /**
         * Set the image using the given resource id.
         *
         * @param optionalImage image
         * @return image
         */
        fun setOptionalImage(optionalImage: CharSequence?): Builder {
            mOptional = optionalImage
            return this
        }

        /**
         * Set the title using the given resource id.
         *
         * @param imageId the title id
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setImage(@StringRes imageId: Int): Builder {
            mImage = mContext.getText(imageId)
            return this
        }

        /**
         * Set the title displayed in the [Dialog].
         *
         * @param title the title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setTitle(title: CharSequence?): Builder {
            mTitle = title
            return this
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @param messageId the message id
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setMessage(@StringRes messageId: Int): Builder {
            mMessage = mContext.getText(messageId)
            return this
        }

        /**
         * Set the message to display.
         *
         * @param message the message
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setMessage(message: CharSequence?): Builder {
            mMessage = message
            return this
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param textId   The resource id of the text to display in the positive button
         * @param listener The [DialogInterface.OnClickListener] to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setPositiveButton(@StringRes textId: Int, listener: CustomDialogInterface.OnClickListener?): Builder {
            mPositiveButtonText = mContext.getText(textId)
            mPositiveButtonListener = listener
            return this
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param text     The text to display in the positive button
         * @param listener The [DialogInterface.OnClickListener] to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setPositiveButton(
            text: CharSequence?,
            listener: CustomDialogInterface.OnClickListener?
        ): Builder {
            mPositiveButtonText = text
            mPositiveButtonListener = listener
            return this
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param textId   The resource id of the text to display in the negative button
         * @param listener The [DialogInterface.OnClickListener] to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setNegativeButton(@StringRes textId: Int, listener: CustomDialogInterface.OnClickListener?): Builder {
            mNegativeButtonText = mContext.getText(textId)
            mNegativeButtonListener = listener
            return this
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param text     The text to display in the negative button
         * @param listener The [DialogInterface.OnClickListener] to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setNegativeButton(
            text: CharSequence?,
            listener: CustomDialogInterface.OnClickListener?
        ): Builder {
            mNegativeButtonText = text
            mNegativeButtonListener = listener
            return this
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @param cancelable the cancelable
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setCancelable(cancelable: Boolean): Builder {
            mCancelable = cancelable
            return this
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         *
         *
         * Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * [//setOnCancelListener(android.content.DialogInterface.OnCancelListener)][.]*.
         *
         * @param onCancelListener the on cancel listener
         * @return This Builder object to allow for chaining of calls to set methods
         * @see .setCancelable
         * @see .//setOnCancelListener
         */
        fun setOnCancelListener(onCancelListener: CustomDialogInterface.OnCancelListener?): Builder {
            mOnCancelListener = onCancelListener
            return this
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @param onDismissListener the on dismiss listener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setOnDismissListener(onDismissListener: CustomDialogInterface.OnDismissListener?): Builder {
            mOnDismissListener = onDismissListener
            return this
        }

        /**
         * Creates an [AlertDialog] with the arguments supplied to this
         * builder.
         *
         *
         * Calling this method does not display the dialog. If no additional
         * processing is needed, [.show] may be called instead to both
         * create and display the dialog.
         *
         * @return the alert dialog
         */
        fun create(): AlertDialog {
            mBuilder = AlertDialog.Builder(mContext)
            val dialogView = mInflater.inflate(R.layout.options_dialog, null)
            mBuilder!!.setView(dialogView)
            mAlertDialog = mBuilder!!.create()
            val tvTitle = dialogView.findViewById<View>(R.id.tvTitle) as TextView
            val tvMessage = dialogView.findViewById<View>(R.id.tvMessage) as TextView
            val btnNegative = dialogView.findViewById<View>(R.id.btnNegative) as Button
            val btnPositive = dialogView.findViewById<View>(R.id.btnPositive) as Button
            val ivImage = dialogView.findViewById<View>(R.id.tvImageDelete) as ImageView
            //set title
            if (mTitle != null) {
                tvTitle.text = mTitle
            } else {
                tvTitle.visibility = View.GONE
            }
            if (mMessage != null) {
                tvMessage.text = mMessage
            }
            //set positive button
            if (mPositiveButtonText != null) {
                btnPositive.text = mPositiveButtonText
                btnPositive.setOnClickListener {
                    mAlertDialog!!.dismiss()
                    if (mPositiveButtonListener != null) {
                        mPositiveButtonListener!!.onClick()
                    }
                }
            } else {
                btnPositive.visibility = View.GONE
            }
            if (mOptional != null && mOptional != "null") {
                //ivImage.setImageResource(R.drawable.ic_info_big)
                ivImage.visibility = View.GONE
            } else {
                ivImage.visibility = View.GONE
            }
            //set negative button
            if (mNegativeButtonText != null) {
                btnNegative.text = mNegativeButtonText
                btnNegative.setOnClickListener {
                    mAlertDialog!!.dismiss()
                    if (mNegativeButtonListener != null) {
                        mNegativeButtonListener!!.onClick()
                    }
                }
            } else {
                btnNegative.visibility = View.GONE
            }
            //set Cancelable
            mAlertDialog!!.setCancelable(mCancelable)
            mAlertDialog!!.setCanceledOnTouchOutside(mCancelable)
            //set cancel listener
            if (mOnCancelListener != null) {
                mAlertDialog!!.setOnCancelListener {
                    if (mOnCancelListener != null) {
                        mOnCancelListener!!.onCancel()
                    }
                }
            }
            //set dismiss listener
            if (mOnDismissListener != null) {
                mAlertDialog!!.setOnDismissListener {
                    if (mOnDismissListener != null) {
                        mOnDismissListener!!.onDismiss()
                    }
                }
            }
            return mAlertDialog!!
        }

        /**
         * Creates an [AlertDialog] with the arguments supplied to this
         * builder and immediately displays the dialog.
         *
         *
         * Calling this method is functionally identical to:
         * <pre>
         * AlertDialog dialog = builder.create();
         * dialog.show();
        </pre> *
         *
         * @return the alert dialog
         */
        fun show(): AlertDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }

        /**
         * Build custom alert dialog.
         *
         * @return the custom alert dialog
         */
        fun build(): CustomAlertDialog {
            return CustomAlertDialog(this)
        }

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        init {
            mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }
}