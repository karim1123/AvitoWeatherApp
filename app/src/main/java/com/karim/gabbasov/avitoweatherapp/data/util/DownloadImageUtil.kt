package com.karim.gabbasov.avitoweatherapp.data.util

import android.widget.ImageView

/**
 * Executes a downloading image into from [imageLink] into [view].
 */
interface DownloadImageUtil {
    fun getImage(imageLink: String, view: ImageView)
}
