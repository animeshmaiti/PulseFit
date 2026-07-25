package com.animesh.pulsefit.ui.components.picktime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun PickHourMinuteSecond(
    initialHour: Int,
    onHourChange: (Int) -> Unit,
    initialMinute: Int,
    onMinuteChange: (Int) -> Unit,
    initialSecond: Int,
    onSecondChange: (Int) -> Unit,
    selectedTextStyle: PickTimeTextStyle = PickTimeTextStyle(
        color = Color(0xFF404040),
        fontSize = 24.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
    ),
    unselectedTextStyle: PickTimeTextStyle = PickTimeTextStyle(
        color = Color(0xFF9F9F9F),
        fontSize = 18.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
    ),
    verticalSpace: Dp = 10.dp,
    horizontalSpace: Dp = 10.dp,
    containerColor: Color = Color(0xFFFFFFFF),
    isLooping: Boolean = false,
    extraRow: Int = 2,
    focusIndicator: PickTimeFocusIndicator = PickTimeFocusIndicator(
        enabled = true,
        widthFull = false,
        background = Color(0xFFFFFFFF),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(4.dp, Color(0xFFEE4720)),
    )
) {
    val displayedHour = initialHour.coerceIn(0, 23)
    val displayedMinute = initialMinute.coerceIn(0, 59)
    val displayedSecond = initialSecond.coerceIn(0, 59)
    val row = extraRow.coerceIn(1, 5)

    val adjustedSelectedTextStyle = if (selectedTextStyle.fontSize < unselectedTextStyle.fontSize) {
        selectedTextStyle.copy(fontSize = unselectedTextStyle.fontSize)
    } else selectedTextStyle

    GenericPickTime(
        selectedTextStyle = adjustedSelectedTextStyle,
        verticalSpace = verticalSpace,
        containerColor = containerColor,
        focusIndicator = focusIndicator
    ){
        NumberWheel(
            items = (0..23).toList(),
            selectedItem = displayedHour,
            onItemSelected = onHourChange,
            space = verticalSpace,
            selectedTextStyle = adjustedSelectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
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
            unselectedTextStyle = unselectedTextStyle,
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
            unselectedTextStyle = unselectedTextStyle,
            extraRow = row,
            isLooping = isLooping
        )
    }
}
