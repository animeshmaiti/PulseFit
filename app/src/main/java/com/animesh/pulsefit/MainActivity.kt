package com.animesh.pulsefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // <-- Import this
import com.animesh.pulsefit.ui.navigation.RootNavHost
import com.animesh.pulsefit.ui.splash.PulseFitSplash
import com.animesh.pulsefit.ui.theme.PulseFitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Install the native Android system splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PulseFitTheme {

                var showSplash by remember {
                    mutableStateOf(true)
                }

                if (showSplash) {
                    PulseFitSplash(
                        onFinished = {
                            showSplash = false
                        }
                    )
                } else {
                    RootNavHost()
                }
            }
        }
    }
}