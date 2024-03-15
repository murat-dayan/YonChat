package com.example.yonchat.utils

import android.app.AlertDialog
import android.content.Context

fun showAlertDialog(context: Context, title: String, message: String,
                    positiveButtonText: String, negativeButtonText: String,
                    onPositiveButtonClicked: () -> Unit,
                    onNegativeButtonClicked: () -> Unit) {

    val builder = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, which ->
            onPositiveButtonClicked()
        }
        .setNegativeButton(negativeButtonText) { dialog, which ->
            onNegativeButtonClicked()
        }

    val alertDialog = builder.create()
    alertDialog.show()
}