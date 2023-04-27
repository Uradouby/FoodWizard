package com.example.foodwizard.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.roundToInt

class PictureUtils {
    companion object{
        fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
// Read in the dimensions of the image on disk
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            val srcWidth = options.outWidth.toFloat()
            val srcHeight = options.outHeight.toFloat()
// Figure out how much to scale down by
            val sampleSize = if (srcHeight <= destHeight && srcWidth <= destWidth) {
                1
            } else {
                val heightScale = srcHeight / destHeight
                val widthScale = srcWidth / destWidth
                minOf(heightScale, widthScale).roundToInt()
            }
// Read in and create final bitmap
            return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
                inSampleSize = sampleSize
            })
        }
    }

}