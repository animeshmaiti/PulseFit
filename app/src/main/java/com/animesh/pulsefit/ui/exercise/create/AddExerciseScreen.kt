package com.animesh.pulsefit.ui.exercise.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.enums.ExerciseCategory
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar
import com.animesh.pulsefit.viewmodel.ExerciseViewModel
import com.animesh.pulsefit.ui.extensions.displayName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(
    rootNavController: NavHostController,
    viewModel: ExerciseViewModel
) {
    var name by rememberSaveable { mutableStateOf("") }

    var description by rememberSaveable { mutableStateOf("") }

    var met by rememberSaveable { mutableStateOf("") }

    var isFavorite by rememberSaveable { mutableStateOf(false) }

    var category by rememberSaveable {
        mutableStateOf(ExerciseCategory.entries.first())
    }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            PulseFitBackTopBar(
                title = "Add Exercise",
                onBack = {
                    rootNavController.popBackStack()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                placeholder = {
                    Text("e.g. Bench Press")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                onValueChange = {
                    name = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Exercise Name")
                },
                singleLine = true
            )

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
                    label = {
                        Text("Category")
                    },
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
                            text = {
                                Text(selection.displayName())
                            },
                            onClick = {
                                category = selection
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            OutlinedTextField(
                value = description,
                placeholder = {
                    Text("Describe the exercise...")
                },
                onValueChange = {
                    description = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                minLines = 4,
                maxLines = 6,
                label = {
                    Text("Description")
                }
            )

            OutlinedTextField(
                value = met,
                placeholder = {
                    Text("e.g. 6.5")
                },
                onValueChange = {

                    if (it.matches(Regex("^\\d*$"))) {
                        met = it
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("MET Value")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isFavorite,
                    onCheckedChange = {
                        isFavorite = it
                    }
                )

                Text("Favorite")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val exercise = Exercise(
                        name = name.trim(),
                        category = category,
                        description = description.trim(),
                        met = met.toFloatOrNull() ?: 0f,
                        isFavorite = isFavorite,
                        isBuiltIn = false
                    )

                    viewModel.createExercise(exercise)
                    rootNavController.popBackStack()
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
}