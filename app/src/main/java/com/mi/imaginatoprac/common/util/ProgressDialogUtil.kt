package com.mi.imaginatoprac.common.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.core.content.ContextCompat
import com.basestructure.app.R

object ProgressDialogUtil {
    private var dialog: Dialog? = null
    fun showProgressDialog(context: Context) {
        if (dialog == null) {
            dialog = Dialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.dialog_progress)
            dialog?.window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        android.R.color.transparent
                    )
                )
            )
            dialog?.setCancelable(false)
        }
        dialog?.show()
    }

    fun hideProgressDialog() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog?.dismiss()
            }
            dialog = null
        }
    }
}
