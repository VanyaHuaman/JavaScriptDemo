package com.example.jsdemo

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class WebAppInterface(private val context: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun addToCalendar(
        beginTime: String?,
        endTime: String?,
        title: String?,
        description: String?,
        eventLocation: String?,
        email: String?
    ) {
        /**
         * might just use UNIX Epoch time
         */

        try {
            val beginLocalDateTime =
                LocalDateTime.parse(beginTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val endLocalDateTime =
                LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            val startMillis: Long = Calendar.getInstance().run {
                set(
                    beginLocalDateTime.year,
                    beginLocalDateTime.monthValue,
                    beginLocalDateTime.dayOfMonth,
                    beginLocalDateTime.hour,
                    beginLocalDateTime.minute
                )
                timeInMillis
            }
            val endMillis: Long = Calendar.getInstance().run {
                set(
                    endLocalDateTime.year,
                    endLocalDateTime.monthValue,
                    endLocalDateTime.dayOfMonth,
                    endLocalDateTime.hour,
                    endLocalDateTime.minute
                )
                timeInMillis
            }

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                .putExtra(CalendarContract.Events.TITLE, title ?: "")
                .putExtra(CalendarContract.Events.DESCRIPTION, description ?: "")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation ?: "")
                .putExtra(Intent.EXTRA_EMAIL, email ?: "")
            startActivity(context, intent, null)
        } catch (e: Exception) {
            Log.d(WEB_VIEW_INTERFACE_TAG, "calendar event exception:$e")
        }
    }

    companion object {
        private const val WEB_VIEW_INTERFACE_TAG = "web_view_interface_tag"
    }
}