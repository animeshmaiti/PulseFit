package com.animesh.pulsefit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.animesh.pulsefit.PulseFitApplication
import com.animesh.pulsefit.ui.exercise.create.AddExerciseScreen
import com.animesh.pulsefit.ui.exercise.create.AddWorkoutScreen
import com.animesh.pulsefit.ui.exercise.details.ExerciseDetailScreen
import com.animesh.pulsefit.ui.exercise.create.EditExerciseScreen
import com.animesh.pulsefit.ui.exercise.create.EditWorkoutScreen
import com.animesh.pulsefit.ui.exercise.details.WorkoutDetailScreen
import com.animesh.pulsefit.ui.exercise.picker.SelectExercisesScreen
import com.animesh.pulsefit.ui.profile.EditProfileScreen
import com.animesh.pulsefit.ui.profile.ProfileScreen
import com.animesh.pulsefit.viewmodel.AddExerciseViewModel
import com.animesh.pulsefit.viewmodel.AddWorkoutViewModel
import com.animesh.pulsefit.viewmodel.EditExerciseViewModel
import com.animesh.pulsefit.viewmodel.ExerciseDetailViewModel
import com.animesh.pulsefit.viewmodel.ExerciseViewModel
import com.animesh.pulsefit.viewmodel.ProfileViewModel
import com.animesh.pulsefit.viewmodel.WorkoutDetailViewModel
import com.animesh.pulsefit.viewmodel.WorkoutSessionViewModel

@Composable
fun RootNavHost() {

    val rootNavController = rememberNavController()
    val app =
        LocalContext.current.applicationContext as PulseFitApplication

    val exerciseViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModel.factory(
            app.exerciseRepository,
            app.workoutRepository
        )
    )
    val addExerciseViewModel: AddExerciseViewModel = viewModel(
        factory = AddExerciseViewModel.factory(
            app.exerciseRepository
        )
    )
    val addWorkoutViewModel: AddWorkoutViewModel = viewModel(
        factory = AddWorkoutViewModel.factory(
            app.exerciseRepository,
            app.workoutRepository,
            app.workoutExerciseRepository,
            app.workoutBuilderRepository

        )
    )
    val exerciseDetailViewModel: ExerciseDetailViewModel = viewModel(
        factory = ExerciseDetailViewModel.factory(
            app.exerciseRepository,
            app.userProfileRepository
        )
    )
    val editExerciseViewModel: EditExerciseViewModel = viewModel(
        factory = EditExerciseViewModel.factory(
            app.exerciseRepository
        )
    )
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.factory(
            app.userProfileRepository
        )
    )

    val workoutDetailViewModel: WorkoutDetailViewModel = viewModel(
        factory = WorkoutDetailViewModel.factory(
            app.workoutRepository,
            app.workoutExerciseRepository,
            app.exerciseRepository,
            app.workoutBuilderRepository
        )
    )
    val workoutSessionViewmodel:WorkoutSessionViewModel= viewModel(
        factory = WorkoutSessionViewModel.factory(
            app.workoutRepository,
            app.workoutExerciseRepository,
            app.exerciseRepository,
            app.userProfileRepository
        )
    )

    NavHost(
        navController = rootNavController,
        startDestination = RootScreen.Main.route
    ) {

        composable(RootScreen.Main.route) {
            MainGraph(
                rootNavController = rootNavController,
                exerciseViewModel = exerciseViewModel,
                addExerciseViewModel = addExerciseViewModel,
                addWorkoutViewModel = addWorkoutViewModel
            )
        }

        composable(RootScreen.AddExercise.route) {
            AddExerciseScreen(
                rootNavController = rootNavController,
                addExerciseViewModel = addExerciseViewModel
            )
        }

        composable(RootScreen.AddWorkout.route) {
            AddWorkoutScreen(
                rootNavController = rootNavController,
                viewModel = addWorkoutViewModel
            )
        }

        composable(RootScreen.SelectExercises.route) {
            SelectExercisesScreen(
                rootNavController = rootNavController,
                viewModel = addWorkoutViewModel
            )
        }
        composable(RootScreen.Profile.route) {
            ProfileScreen(
                viewModel = profileViewModel,
                rootNavController = rootNavController
            )
        }

        composable(
            route = RootScreen.EditExercise.route,
            arguments = listOf(
                navArgument("exerciseId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val exerciseId =
                backStackEntry.arguments?.getLong("exerciseId")
                    ?: return@composable

            EditExerciseScreen(
                exerciseId = exerciseId,
                rootNavController = rootNavController,
                viewModel = editExerciseViewModel
            )
        }

        composable(
            route = RootScreen.ExerciseDetail.route
        ) { backStackEntry ->

            val exerciseId =
                backStackEntry.arguments
                    ?.getString("exerciseId")
                    ?.toLongOrNull() ?: return@composable

            ExerciseDetailScreen(
                exerciseId = exerciseId,
                rootNavController = rootNavController,
                viewModel = exerciseDetailViewModel
            )
        }

        composable(
            route = RootScreen.WorkoutDetail.route
        ) { backStackEntry ->

            val workoutId =
                backStackEntry.arguments
                    ?.getString("workoutId")
                    ?.toLongOrNull() ?: return@composable

            WorkoutDetailScreen(
                workoutId = workoutId,
                rootNavController = rootNavController,
                viewModel = workoutDetailViewModel,
                sessionViewModel = workoutSessionViewmodel
            )
        }

        composable(
            route = RootScreen.EditWorkout.route,
            arguments = listOf(
                navArgument("workoutId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val workoutId =
                backStackEntry
                    .arguments
                    ?.getLong("workoutId")
                    ?: return@composable

            EditWorkoutScreen(
                workoutId = workoutId,
                rootNavController = rootNavController,
                viewModel = addWorkoutViewModel
            )
        }

        composable(RootScreen.EditProfile.route) {
            EditProfileScreen(
                rootNavController = rootNavController,
                viewModel = profileViewModel
            )
        }
//
//        composable(
//            RootScreen.EditWorkout.route
//        ) {
//            EditWorkoutScreen(rootNavController)
//        }
//
//        composable(
//            RootScreen.WorkoutSession.route
//        ) {
//            WorkoutSessionScreen(rootNavController)
//        }
    }
}