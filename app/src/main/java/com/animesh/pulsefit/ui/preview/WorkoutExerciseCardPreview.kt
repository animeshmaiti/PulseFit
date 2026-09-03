import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.enums.BreakType
import com.animesh.pulsefit.data.enums.ExerciseCategory
import com.animesh.pulsefit.ui.exercise.components.WorkoutExerciseCard
import com.animesh.pulsefit.ui.exercise.create.model.WorkoutExerciseUi
import com.animesh.pulsefit.ui.theme.PulseFitTheme

@Preview(
    name = "Workout Exercise Card",
    showBackground = true
)
@Composable
private fun WorkoutExerciseCardPreview() {
    PulseFitTheme {
        WorkoutExerciseCard(
            workoutExercise = WorkoutExerciseUi(
                exercise = Exercise(
                    id = 1,
                    name = "Push Ups",
                    category = ExerciseCategory.ARMS, // Use any valid enum
                    description = "Standard push-ups with proper form.",
                    defaultDuration = 60,
                    met = 8f,
                    isFavorite = true,
                    isBuiltIn = true
                ),
                duration = 90,
                breakType = BreakType.MANUAL,
                breakDuration = 30
            ),
            onRemove = {},
            onEditDuration = {},
            onEditBreakDuration = {},
            onBreakTypeChange = {}
        )
    }
}