package com.animesh.pulsefit.ui.exercise.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.components.picktime.PickHourMinuteSecond

@Composable
fun ExerciseSetupContent(
    duration: Int,
    onDurationChange: (Int) -> Unit
) {

    val hour = duration / 3600
    val minute = (duration % 3600) / 60
    val second = duration % 60

    Column {

        Card(
            modifier = Modifier.fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.coverimage),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Duration",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold
        )

        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {

            PickHourMinuteSecond(
                initialHour = hour,
                onHourChange = { newHour ->
                    onDurationChange(
                        newHour * 3600 +
                                minute * 60 +
                                second
                    )
                },

                initialMinute = minute,
                onMinuteChange = { newMinute ->
                    onDurationChange(
                        hour * 3600 +
                                newMinute * 60 +
                                second
                    )
                },

                initialSecond = second,
                onSecondChange = { newSecond ->
                    onDurationChange(
                        hour * 3600 +
                                minute * 60 +
                                newSecond
                    )
                },

                isLooping = true
            )
        }
    }
}