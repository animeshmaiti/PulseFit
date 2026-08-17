package com.animesh.pulsefit.ui.navigation

sealed class RootScreen(val route: String) {

    data object Main : RootScreen("main")

    data object AddExercise : RootScreen("add_exercise")
    data object EditExercise : RootScreen("edit_exercise/{exerciseId}") {
        fun createRoute(id: Long) = "edit_exercise/$id"
    }
    data object ExerciseDetail : RootScreen("exercise_detail/{exerciseId}") {
        fun createRoute(id: Long) = "exercise_detail/$id"
    }

    //  add workout
    data object AddWorkout : RootScreen("add_workout")
    data object SelectExercises: RootScreen("select_exercises")

    data object WorkoutDetail : RootScreen("workout_detail/{workoutId}") {
        fun createRoute(id: Long) = "workout_detail/$id"
    }
    data object EditWorkout : RootScreen("edit_workout/{workoutId}") {
        fun createRoute(id: Long) = "edit_workout/$id"
    }

    data object WorkoutSession : RootScreen("workout_session/{workoutId}") {
        fun createRoute(id: Long) = "workout_session/$id"
    }

    data object Profile : RootScreen("profile")
    data object EditProfile : RootScreen("edit_profile")
}