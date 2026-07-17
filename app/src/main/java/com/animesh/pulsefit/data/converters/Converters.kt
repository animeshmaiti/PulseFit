package com.animesh.pulsefit.data.converters

import androidx.room.TypeConverter
import com.animesh.pulsefit.data.enums.BreakType
import com.animesh.pulsefit.data.enums.ExerciseCategory

class Converters {

    @TypeConverter
    fun fromExerciseCategory(category: ExerciseCategory): String {
        return category.name
    }

    @TypeConverter
    fun toExerciseCategory(value: String): ExerciseCategory {
        return ExerciseCategory.valueOf(value)
    }

    @TypeConverter
    fun fromBreakType(breakType: BreakType): String {
        return breakType.name
    }

    @TypeConverter
    fun toBreakType(value: String): BreakType {
        return BreakType.valueOf(value)
    }
}