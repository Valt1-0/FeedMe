package com.example.feedme.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.feedme.R
@Composable
fun LoadImageFromUrl(context : Context, url: String, modifier: Modifier = Modifier) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
            placeholder(R.drawable.defaultcard)
            error(R.drawable.defaultcard)
        }).build()
    )

    LaunchedEffect(url) {
        try {
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()
            val result = Coil.imageLoader(context).execute(request).drawable
            val bitmap = result?.toBitmap() ?: BitmapFactory.decodeResource(context.resources, R.drawable.defaultcard)
            image = Bitmap.createScaledBitmap(bitmap, 300, 300, false).asImageBitmap()
           /// image = (result?.toBitmap() ?: BitmapFactory.decodeResource(context.resources, R.drawable.jonathan)).asImageBitmap()
        } catch (ex : Exception) {
            Log.e("LoadImageFromUrl", "Failed to load image: $url")
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.defaultcard)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true)
            image = scaledBitmap.asImageBitmap()
        }
    }

    image?.let {
        Image(
            contentDescription = null,
            modifier = modifier.fillMaxWidth().height(150.dp),
            contentScale = ContentScale.Crop,
            painter = painter,
        )
    }
}