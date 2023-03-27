package com.example.feedme.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.graphics.scale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.feedme.R
@Composable
fun LoadImageFromUrl(context: Context, url: String, modifier: Modifier = Modifier) {
    var image by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .skipMemoryCache(false)
            .placeholder(R.drawable.defaultcard)
            .error(R.drawable.defaultcard)
            .override(300, 300)

        val glide = Glide.with(context)
        val futureTarget = glide.asBitmap().load(url).apply(requestOptions).submit()

        try {
            image = futureTarget.get()
        } catch (ex: Exception) {
            Log.e("LoadImageFromUrl", "Failed to load image: $url")

            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .placeholder(R.drawable.defaultcard)
                .error(R.drawable.defaultcard)
                .override(300, 300)

            // Préchargement de l'image dans le cache
            glide.asBitmap().load(url).apply(requestOptions).preload()

            // Chargement de l'image depuis le cache
            glide.asBitmap().load(url).apply(requestOptions).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    image = resource.scale(300, 300)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    Log.e("LoadImageFromUrl", "Failed to load image from cache: $url")
                    image = BitmapFactory.decodeResource(context.resources, R.drawable.defaultcard).scale(300, 300)
                }
            })
        }
    }

    image?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = modifier.fillMaxWidth().height(150.dp),
            contentScale = ContentScale.Crop
        )
    }
}

