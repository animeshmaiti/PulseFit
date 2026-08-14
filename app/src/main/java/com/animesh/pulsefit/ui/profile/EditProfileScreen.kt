package com.animesh.pulsefit.ui.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController

import com.animesh.pulsefit.data.entity.UserProfile
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar
import com.animesh.pulsefit.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    rootNavController: NavHostController,
    viewModel: ProfileViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val profile = viewModel.profile

    if (profile == null) {
        return
    }

    var name by rememberSaveable(profile.id) {
        mutableStateOf(profile.name)
    }

    var age by rememberSaveable(profile.id) {
        mutableStateOf(
            if (profile.age > 0) {
                profile.age.toString()
            } else {
                ""
            }
        )
    }

    var height by rememberSaveable(profile.id) {
        mutableStateOf(
            if (profile.heightCm > 0f) {
                profile.heightCm.toString()
            } else {
                ""
            }
        )
    }

    var weight by rememberSaveable(profile.id) {
        mutableStateOf(
            if (profile.weightKg > 0f) {
                profile.weightKg.toString()
            } else {
                ""
            }
        )
    }

    var medicalCondition by rememberSaveable(profile.id) {
        mutableStateOf(profile.medicalCondition)
    }

    var bloodType by rememberSaveable(profile.id) {
        mutableStateOf(profile.bloodType)
    }

    var bloodTypeExpanded by remember {
        mutableStateOf(false)
    }

    var profileImageUri by rememberSaveable(profile.id) {
        mutableStateOf(profile.profileImageUri)
    }

    val context = LocalContext.current

    /*
     * Open Android's document picker.
     *
     * We use OpenDocument instead of GetContent because it allows
     * us to request persistent read access to the selected image.
     */
    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            uri ?: return@rememberLauncherForActivityResult

            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Some providers may not support persistable permissions.
                // The URI can still be used during the current session.
            }

            profileImageUri = uri.toString()
        }

    /*
     * Convert the saved URI into a Bitmap.
     *
     * No Coil or network dependency is required.
     */
    val profileBitmap = remember(profileImageUri) {

        profileImageUri?.let { uriString ->

            try {

                val uri = Uri.parse(uriString)

                context.contentResolver
                    .openInputStream(uri)
                    ?.use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    }

            } catch (_: Exception) {
                null
            }
        }
    }

    Scaffold(
        topBar = {
            PulseFitBackTopBar(
                title = "Edit Profile",
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
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /*
             * Profile image
             */

            if (profileBitmap != null) {

                Image(
                    bitmap = profileBitmap.asImageBitmap(),
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            } else {

                // No image selected.
                // You can replace this later with your own profile icon.
                Text(
                    text = "No profile image"
                )
            }

            Button(
                onClick = {
                    imagePicker.launch(
                        arrayOf("image/*")
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (profileBitmap != null) {
                        "Change Profile Image"
                    } else {
                        "Choose Profile Image"
                    }
                )
            }
        }

        /*
         * Name
         */

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Name")
            },
            placeholder = {
                Text("Your Name")
            },
            singleLine = true
        )

        /*
         * Age
         */

        OutlinedTextField(
            value = age,
            onValueChange = {

                if (it.all(Char::isDigit)) {
                    age = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Age")
            },
            placeholder = {
                Text("e.g. 25")
            },
            singleLine = true
        )

        /*
         * Height
         */

        OutlinedTextField(
            value = height,
            onValueChange = {

                if (
                    it.matches(
                        Regex("^\\d*(\\.\\d*)?$")
                    )
                ) {
                    height = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Height (cm)")
            },
            placeholder = {
                Text("e.g. 175")
            },
            singleLine = true
        )

        /*
         * Weight
         */

        OutlinedTextField(
            value = weight,
            onValueChange = {

                if (
                    it.matches(
                        Regex("^\\d*(\\.\\d*)?$")
                    )
                ) {
                    weight = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Weight (kg)")
            },
            placeholder = {
                Text("e.g. 70")
            },
            singleLine = true
        )

        /*
         * Medical condition
         */

        OutlinedTextField(
            value = medicalCondition,
            onValueChange = {
                medicalCondition = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Medical Condition")
            },
            placeholder = {
                Text("None")
            },
            minLines = 3,
            maxLines = 5
        )

        /*
         * Blood type
         */

        ExposedDropdownMenuBox(
            expanded = bloodTypeExpanded,
            onExpandedChange = {
                bloodTypeExpanded = it
            }
        ) {

            OutlinedTextField(
                value = bloodType.ifBlank {
                    "Not specified"
                },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor(
                        MenuAnchorType.PrimaryNotEditable
                    )
                    .fillMaxWidth(),
                label = {
                    Text("Blood Type")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = bloodTypeExpanded
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = bloodTypeExpanded,
                onDismissRequest = {
                    bloodTypeExpanded = false
                }
            ) {

                listOf(
                    "A+",
                    "A-",
                    "B+",
                    "B-",
                    "AB+",
                    "AB-",
                    "O+",
                    "O-"
                ).forEach { type ->

                    DropdownMenuItem(
                        text = {
                            Text(type)
                        },
                        onClick = {

                            bloodType = type
                            bloodTypeExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        /*
         * Save
         */

        Button(
            onClick = {

                val updatedProfile = profile.copy(

                    name = name
                        .trim()
                        .ifBlank {
                            "Your Name"
                        },

                    age = age.toIntOrNull() ?: 0,

                    heightCm =
                    height.toFloatOrNull() ?: 0f,

                    weightKg =
                    weight.toFloatOrNull() ?: 0f,

                    profileImageUri =
                    profileImageUri,

                    medicalCondition =
                    medicalCondition.trim(),

                    bloodType =
                    bloodType
                )

                viewModel.saveProfile(
                    updatedProfile
                )

                rootNavController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Save")
        }
    }
}