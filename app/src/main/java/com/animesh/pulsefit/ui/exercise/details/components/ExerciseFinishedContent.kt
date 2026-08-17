package com.animesh.pulsefit.ui.exercise.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.R
import java.util.Locale

@Composable
fun ExerciseFinishedContent(
    exerciseName: String,
    duration: String,
    calories: Float,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Spacer(Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.checked),
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Workout Complete!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Great job! Keep up the consistency.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {

                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Text(
                        text = "Workout Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        StatTile(
                            modifier = Modifier.weight(1f),
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.timer_24px),
                                    null,
                                    tint = Color(0xFF66BB6A)
                                )
                            },
                            title = "Duration",
                            value = duration
                        )

                        StatTile(
                            modifier = Modifier.weight(1f),
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.local_fire_department_24px),
                                    null,
                                    tint = Color(0xFFFF7043)
                                )
                            },
                            title = "Calories",
                            value = String.format(
                                Locale.getDefault(),
                                "%.2f kcal",
                                calories
                            )
                        )
                    }

                    StatTile(
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.exercise_24px),
                                null,
                                tint = Color(0xFF7E57C2)
                            )
                        },
                        title = "Exercise",
                        value = exerciseName
                    )
                }
            }
        }
    }
}