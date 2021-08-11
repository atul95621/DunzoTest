package com.example.shipsytest.util

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ProgressBar
import com.example.dunzotest.R


object ProgressBarClass {
    var dialog: Dialog? = null

    fun progressBarCalling(context: Context?) {
        dialog = Dialog(context!!)
        dialog?.setContentView(R.layout.parent_progressbar)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // making backgrnd color tarnsparent code begind progress circle bar
        val progressBar = dialog!!.findViewById<View>(R.id.progressBar) as ProgressBar
        val backViewProgressbar = dialog!!.findViewById(R.id.backViewProgressbar) as View
        progressBar.visibility = View.VISIBLE
        progressBar.isIndeterminate = true
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window!!.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        dialog!!.setCancelable(false)
        backViewProgressbar.visibility = View.VISIBLE
        dialog!!.show()
    }
}