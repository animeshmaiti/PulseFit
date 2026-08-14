package com.animesh.pulsefit.ui.profile.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.data.entity.UserProfile
import com.animesh.pulsefit.ui.profile.utils.calculateBmi
import com.animesh.pulsefit.ui.profile.utils.formatValue

@Composable
fun BmiCard(
    profile: UserProfile?
) {
    val bmi = calculateBmi(
        heightCm = profile?.heightCm ?: 0f,
        weightKg = profile?.weightKg ?: 0f
    )

    val bmiLabel: String
    val bmiColor: Color

    when {

        bmi == null -> {
            bmiLabel = "Not available"
            bmiColor = MaterialTheme.colorScheme.onSurfaceVariant
        }

        bmi < 18.5f -> {
            bmiLabel = "Underweight"
            bmiColor = Color(0xFFFFC107)
        }

        bmi < 25f -> {
            bmiLabel = "Normal"
            bmiColor = Color(0xFF4CAF50)
        }

        else -> {
            bmiLabel = "Overweight"
            bmiColor = Color(0xFFF44336)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "BMI",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = bmi?.formatValue() ?: "--",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = bmiLabel,
                color = bmiColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}