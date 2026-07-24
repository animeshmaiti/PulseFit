package com.animesh.pulsefit.ui.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.ui.exercise.components.ExerciseCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisePickerBottomSheet(
    exercises: List<Exercise>,
    onDismiss: () -> Unit,
    onAdd: (List<Exercise>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    val selectedExercises = remember {
        mutableStateListOf<Exercise>()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {

        Text(
            text = "Select Exercises",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )

        LazyColumn {
            items(exercises, key = { it.id }) { exercise ->

                val selected = selectedExercises.contains(exercise)

                ExerciseCard(
                    exercise = exercise,
                    selected = selected,
                    onClick = {
                        if (selected) {
                            selectedExercises.remove(exercise)
                        } else {
                            selectedExercises.add(exercise)
                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    onAdd(selectedExercises.toList())
                    onDismiss()
                },
                enabled = selectedExercises.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Add (${selectedExercises.size})")
            }
        }
    }
}