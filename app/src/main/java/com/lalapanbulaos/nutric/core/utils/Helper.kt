package com.lalapanbulaos.nutric.core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun checkPermissionFor(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

fun formatIsoDate(isoDateString: String?): String {
    return isoDateString?.let {
        try {
            // ISO 8601 format should include 'Z' for UTC time zone
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC") // Set timezone to UTC for ISO dates
            }
            val targetFormat = SimpleDateFormat("EEEE, MM/dd/yyyy hh:mm a", Locale.getDefault())
            val date = isoFormat.parse(it)
            date?.let { parsedDate -> targetFormat.format(parsedDate) } ?: "Unknown Date"
        } catch (e: Exception) {
            "Invalid Date"
        }
    } ?: "Unknown Date"
}

