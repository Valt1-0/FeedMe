package com.example.feedme.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.feedme.R

@Composable
fun LoadImageFromUrl(context: Context, url: String, modifier: Modifier = Modifier) {

    Image(
        painter = // Set options for the image request, such as resizing or caching.
        // For example:
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = url)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .error(R.drawable.defaultcard)
                .diskCachePolicy(CachePolicy.ENABLED)
                .apply(block = fun ImageRequest.Builder.() {
                    // Set options for the image request, such as resizing or caching.
                    // For example:
                    size(300, 300)
                    placeholder(R.drawable.defaultcard)
                }).build()
        ),
        contentDescription = "Image du restaurant",
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        contentScale = ContentScale.Crop
    )
}

