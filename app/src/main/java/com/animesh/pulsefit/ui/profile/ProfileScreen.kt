package com.animesh.pulsefit.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.ui.profile.components.BloodTypeCard
import com.animesh.pulsefit.ui.profile.components.BmiCard
import com.animesh.pulsefit.ui.profile.components.MedicalConditionCard
import com.animesh.pulsefit.ui.profile.components.ProfileHeader
import com.animesh.pulsefit.ui.profile.components.ProfileStats
import com.animesh.pulsefit.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    rootNavController: NavHostController,
    viewModel: ProfileViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val profile = viewModel.profile

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("Profile")
                },

                navigationIcon = {
                    IconButton(
                        onClick = {
                            rootNavController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                R.drawable.arrow_back_24px
                            ),
                            contentDescription = "Back"
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = {
                            rootNavController.navigate(RootScreen.EditProfile.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                R.drawable.edit_24px
                            ),
                            contentDescription = "Edit"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileHeader(profile)

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            ProfileStats(profile)

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            BmiCard(profile)

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            MedicalConditionCard(profile)

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            BloodTypeCard(profile)
        }
    }
}