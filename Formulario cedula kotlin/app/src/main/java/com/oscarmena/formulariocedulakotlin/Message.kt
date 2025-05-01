package com.oscarmena.formulariocedulakotlin

import android.content.Context
import android.widget.Toast

object Message {
    fun message(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
