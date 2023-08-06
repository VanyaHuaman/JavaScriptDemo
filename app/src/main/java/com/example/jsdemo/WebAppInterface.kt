package com.example.jsdemo

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.util.Calendar


class WebAppInterface(private val context: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun addToCalendar(
        beginTime: Calendar,
        endTime: Calendar,
        title: String?,
        description: String?,
        eventLocation: String?,
        email: String?
    ) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.DESCRIPTION, description)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation)
            .putExtra(Intent.EXTRA_EMAIL, email)
        startActivity(context, intent, null)
    }
}