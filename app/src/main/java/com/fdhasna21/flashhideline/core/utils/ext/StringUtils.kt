package com.fdhasna21.flashhideline.core.utils.ext

import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import com.fdhasna21.flashhideline.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.toRelativeTime(): String {
    if (this.isBlank()) return ""
    return try {
        val timeInMillis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.parse(this).toEpochMilli()
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            sdf.parse(this)?.time ?: return ""
        }

        DateUtils.getRelativeTimeSpanString(
            timeInMillis,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()
    } catch (e: Exception) {
        ""
    }
}

fun String?.toRelativeTimeString(context: Context): String {
    if (this.isNullOrBlank()) return ""

    return try {
        // Parse format ISO 8601 dari News API
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = sdf.parse(this) ?: return this
        val now = Date().time
        val diffInMillis = now - date.time

        if (diffInMillis < 60_000) {
            return context.getString(R.string.time_just_now)
        }

        val minutes = diffInMillis / (1000 * 60)
        val hours = minutes / 60
        val days = hours / 24

        when {
            minutes < 60 -> context.getString(R.string.time_minutes_ago, minutes)
            hours < 24 -> context.getString(R.string.time_hours_ago, hours)
            days == 1L -> context.getString(R.string.time_yesterday)
            days < 7 -> context.getString(R.string.time_days_ago, days)
            else -> {
                val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                outputFormat.format(date)
            }
        }
    } catch (e: Exception) {
        this
    }
}