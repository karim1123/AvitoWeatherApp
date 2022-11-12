package com.karim.gabbasov.avitoweatherapp.data.util

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Util to aid with convert unixTime to Calendar.
 */
class DownloadImageUtilImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) : DownloadImageUtil {
    /**
     * Executes a downloading image into from [imageLink] into [view].
     */
    override fun getImage(imageLink: String, view: ImageView) {
        val request = ImageRequest.Builder(context)
            .crossfade(true)
            .crossfade(500)
            .data(imageLink)
            .target(view)
            .build()
        imageLoader.enqueue(request)
    }
}
