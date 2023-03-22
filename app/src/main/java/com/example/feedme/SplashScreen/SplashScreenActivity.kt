package com.example.feedme.SplashScreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.feedme.Onboarding.OnboardingActivity
import com.example.feedme.R
import com.example.feedme.network.CheckNetworkConnexion
import com.example.feedme.ui.theme.FeedMeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch


class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedMeTheme {
                MainContent()
                if (CheckNetworkConnexion().isConnectedToInternet(this)) {
                    val coroutineScope = rememberCoroutineScope()
                    LaunchedEffect(key1 = Unit)
                    {
                        coroutineScope.launch(Dispatchers.Main) {
                            delay(5000)
                            startActivity(
                                Intent(
                                    applicationContext,
                                    OnboardingActivity::class.java
                                )
                            )
                            finish()
                        }

                    }
                } else {
                    Snackbar { reloadActivity() }
                }
            }
        }
    }

    fun reloadActivity() {
        val intent = Intent(applicationContext, SplashScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}


@Composable
fun MainContent() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Content1(imageLoader)
}

@Composable
fun Content1(imageLoader: ImageLoader) {
    Surface(color = colors.primary) {
        LinearProgressIndicator(Modifier.fillMaxWidth(), color = colors.secondary)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = R.drawable.feedmelogo2)
                        .apply(block = fun ImageRequest.Builder.() {
                            size(Size.ORIGINAL)
                        }).build(),
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(250.dp)
            )
        }
    }

}


