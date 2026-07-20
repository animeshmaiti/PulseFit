package com.animesh.pulsefit.ui.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar

@Composable
fun AddExerciseScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            PulseFitBackTopBar(
                title = "Add Exercise",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text="add exercise screen"
            )
        }
    }
}