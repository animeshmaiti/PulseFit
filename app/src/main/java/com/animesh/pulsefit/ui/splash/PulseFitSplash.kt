package com.animesh.pulsefit.ui.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.R
import kotlinx.coroutines.delay

@Composable
fun PulseFitSplash(
    onFinished: () -> Unit
) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.65f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "logoScale"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(900)
        onFinished()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.CenterVertically
            )
        ) {

            Image(
                painter = painterResource(R.drawable.pulsefitlauncher),
                contentDescription = "PulseFit",
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale)
            )

            Image(
                painter = painterResource(R.drawable.pulsefittextvec),
                contentDescription = "PulseFit",
                modifier = Modifier
                    .width(100.dp)
                    .height(19.dp)
                    .scale(scale)
            )
        }
    }

}