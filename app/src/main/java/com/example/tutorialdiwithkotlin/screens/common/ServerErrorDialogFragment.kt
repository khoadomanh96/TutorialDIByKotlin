package com.example.tutorialdiwithkotlin.screens.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.tutorialdiwithkotlin.R

/**
 * Created by khoado on 05,February,2020
 */


class ServerErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.apply {
            setTitle(R.string.server_error_dialog_title)
            setMessage(R.string.server_error_dialog_message)
            setPositiveButton(
                R.string.server_error_dialog_button_caption
            ) { _, _ -> dismiss()
            }
        }
        return alertDialogBuilder.create()
    }

    companion object {
        fun newInstance() : ServerErrorDialogFragment {
            return ServerErrorDialogFragment()
        }
    }
}
