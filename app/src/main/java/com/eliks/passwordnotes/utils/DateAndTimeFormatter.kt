package com.eliks.passwordnotes.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateAndTimeFormatter {//TODO make good formatter

    fun formatTimestamp(created: Long, edited: Long): String {
        val actual = if (edited > created) edited else created
        val prefix = if (edited > created) "edited" else "created"
        return "$prefix ${formatDate(actual)}"
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

}