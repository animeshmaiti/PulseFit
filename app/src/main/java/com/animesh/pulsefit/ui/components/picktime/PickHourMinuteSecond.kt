package com.animesh.pulsefit.ui.components.picktime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animesh.pulsefit.ui.components.picktime.components.GenericPickTime
import com.animesh.pulsefit.ui.components.picktime.components.NumberWheel
import com.animesh.pulsefit.ui.components.picktime.utils.PickTimeFocusIndicator
import com.animesh.pulsefit.ui.components.picktime.utils.PickTimeTextStyle

private const val MAX_HOURS = 12

@Composable
fun PickHourMinuteSecond(
    initialHour: Int,
    onHourChange: (Int) -> Unit,
    initialMinute: Int,
    onMinuteChange: (Int) -> Unit,
    initialSecond: Int,
    onSecondChange: (Int) -> Unit,
    selectedTextStyle: PickTimeTextStyle? = null,
    unselectedTextStyle: PickTimeTextStyle? = null,
    verticalSpace: Dp = 10.dp,
    horizontalSpace: Dp = 10.dp,
    containerColor: Color = Color.Unspecified,
    isLooping: Boolean = false,
    extraRow: Int = 2,
    focusIndicator: PickTimeFocusIndicator? = null
) {
    val colorScheme = MaterialTheme.colorScheme

    val selectedStyle = selectedTextStyle ?: PickTimeTextStyle(
        color = colorScheme.primary,
        fontSize = 24.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold
    )

    val unselectedStyle = unselectedTextStyle ?: PickTimeTextStyle(
        color = colorScheme.onSurfaceVariant,
        fontSize = 18.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal
    )

    val backgroundColor =
        if (containerColor == Color.Unspecified)
            colorScheme.surfaceContainerHigh
        else
            containerColor

    val indicator = focusIndicator ?: PickTimeFocusIndicator(
        enabled = true,
        widthFull = false,
        background = colorScheme.surfaceContainerHighest,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            2.dp,
            colorScheme.primary
        )
    )
    val displayedHour = initialHour.coerceIn(0, MAX_HOURS)
    val displayedMinute = initialMinute.coerceIn(0, 59)
    val displayedSecond = initialSecond.coerceIn(0, 59)
    val row = extraRow.coerceIn(1, 5)

    val adjustedSelectedTextStyle = if (selectedStyle.fontSize < unselectedStyle.fontSize) {
        selectedStyle.copy(fontSize = unselectedStyle.fontSize)
    } else selectedStyle

    GenericPickTime(
        selectedTextStyle = adjustedSelectedTextStyle,
        verticalSpace = verticalSpace,
        containerColor = backgroundColor,
        focusIndicator = indicator
    ){
        NumberWheel(
            items = (0..MAX_HOURS).toList(),
            selectedItem = displayedHour,
            onItemSelected = onHourChange,
            space = verticalSpace,
            selectedTextStyle = adjustedSelectedTextStyle,
            unselectedTextStyle = unselectedStyle,
            extraRow = row,
            isLooping = isLooping,
        )
        Spacer(modifier = Modifier.width(horizontalSpace))
        Text(
            text = ":",
            style = adjustedSelectedTextStyle.toTextStyle()
        )
        Spacer(modifier = Modifier.width(horizontalSpace))
        NumberWheel(
            items = (0..59).toList(),
            selectedItem = displayedMinute,
            onItemSelected = onMinuteChange,
            space = verticalSpace,
            selectedTextStyle = adjustedSelectedTextStyle,
            unselectedTextStyle = unselectedStyle,
            extraRow = row,
            isLooping = isLooping
        )
        Spacer(modifier = Modifier.width(horizontalSpace))
            Text(
                text = ":",
                style = adjustedSelectedTextStyle.toTextStyle()
            )
        Spacer(modifier = Modifier.width(horizontalSpace))
        NumberWheel(
            items = (0..59).toList(),
            selectedItem = displayedSecond,
            onItemSelected = onSecondChange,
            space = verticalSpace,
            selectedTextStyle = adjustedSelectedTextStyle,
            unselectedTextStyle = unselectedStyle,
            extraRow = row,
            isLooping = isLooping
        )
    }
}
