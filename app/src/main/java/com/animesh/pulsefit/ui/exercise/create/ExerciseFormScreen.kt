package com.animesh.pulsefit.ui.exercise.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.enums.ExerciseCategory
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar
import com.animesh.pulsefit.ui.extensions.displayName
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseFormScreen(
    title: String,
    initialExercise: Exercise?,
    onSave: suspend (Exercise) -> Boolean,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var name by rememberSaveable(initialExercise?.id) {
        mutableStateOf(initialExercise?.name ?: "")
    }

    var description by rememberSaveable(initialExercise?.id) {
        mutableStateOf(initialExercise?.description ?: "")
    }

    var met by rememberSaveable(initialExercise?.id) {
        mutableStateOf(initialExercise?.met?.toString() ?: "")
    }

    var isFavorite by rememberSaveable(initialExercise?.id) {
        mutableStateOf(initialExercise?.isFavorite ?: false)
    }

    var category by rememberSaveable(initialExercise?.id) {
        mutableStateOf(
            initialExercise?.category ?: ExerciseCategory.entries.first()
        )
    }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            PulseFitBackTopBar(
                title = title,
                onBack = onBack
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            // Sticky bottom container for the Save button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val exercise = Exercise(
                            id = initialExercise?.id ?: 0,
                            name = name.trim(),
                            category = category,
                            description = description.trim(),
                            defaultDuration = initialExercise?.defaultDuration ?: 30,
                            met = met.toFloatOrNull() ?: 0f,
                            isFavorite = isFavorite,
                            isBuiltIn = initialExercise?.isBuiltIn ?: false
                        )

                        scope.launch {
                            val success = onSave(exercise)
                            if (!success) {
                                snackbarHostState.showSnackbar("Exercise already exists.")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = name.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Exercise name
            OutlinedTextField(
                value = name,
                placeholder = { Text("e.g. Bench Press") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Exercise Name") },
                singleLine = true
            )

            // Category
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    value = category.displayName(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ExerciseCategory.entries.forEach { selection ->
                        DropdownMenuItem(
                            text = { Text(selection.displayName()) },
                            onClick = {
                                category = selection
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            // Description
            OutlinedTextField(
                value = description,
                placeholder = { Text("Describe the exercise...") },
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 6,
                label = { Text("Description") }
            )

            // MET
            OutlinedTextField(
                value = met,
                placeholder = { Text("e.g. 6.5") },
                onValueChange = {
                    if (it.matches(Regex("^\\d*(\\.\\d*)?$"))) {
                        met = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("MET Value") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            // Favorite
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isFavorite,
                    onCheckedChange = { isFavorite = it }
                )
                Text("Favorite")
            }
        }
    }
}