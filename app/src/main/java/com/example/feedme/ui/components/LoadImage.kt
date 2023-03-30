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
//    var image : Bitmap? =  null
//
//    LaunchedEffect(url) {
//        val requestOptions = RequestOptions()
//            .diskCacheStrategy(DiskCacheStrategy.DATA)
//            .skipMemoryCache(false)
//            .signature(ObjectKey(url))
//            .placeholder(R.drawable.defaultcard)
//            .error(R.drawable.defaultcard)
//            .override(300, 300)
//
//        val glide = Glide.with(context)
//        val futureTarget = glide.asBitmap().load(url).apply(requestOptions).submit()
//
//        try {
//            image = futureTarget.get()
//        } catch (ex: Exception) {
//            Log.e("LoadImageFromUrl", "Failed to load image: $url")
//
//            val requestOptions = RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .skipMemoryCache(false)
//                .placeholder(R.drawable.defaultcard)
//                .error(R.drawable.defaultcard)
//                .override(300, 300)
//
//            // Préchargement de l'image dans le cache
//            glide.asBitmap().load(url).apply(requestOptions).preload()
//
//            // Chargement de l'image depuis le cache
//            glide.asBitmap().load(url).apply(requestOptions).into(object : SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    image = resource.scale(300, 300)
//                }
//
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    Log.e("LoadImageFromUrl", "Failed to load image from cache: $url")
//                    image = BitmapFactory.decodeResource(context.resources, R.drawable.defaultcard)
//                        .scale(300, 300)
//                }
//            })
//        }
//    }
//
//    image?.let {
//        Image(
//            bitmap = it.asImageBitmap(),
//            contentDescription = null,
//            modifier = modifier
//                .fillMaxWidth()
//                .height(150.dp),
//            contentScale = ContentScale.Crop
//        )
//    }
}

