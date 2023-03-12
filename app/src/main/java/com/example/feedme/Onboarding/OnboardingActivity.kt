package com.example.feedme.Onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.feedme.MainActivity
import com.example.feedme.ui.theme.OnBoardingTheme

class OnboardingActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val PREFS_NAME = "OnboardPREF"
        val PREF_KEY_ONBOARDING_COMPLETE = "onboardingComplete"
        val prefs = applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val onboardingComplete = prefs.getBoolean(PREF_KEY_ONBOARDING_COMPLETE, false)

        if (onboardingComplete) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            OnBoardingTheme() {
                OnboardingScreen(onboardingItems) {
                    prefs.edit().putBoolean(PREF_KEY_ONBOARDING_COMPLETE, true).apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}


