package com.animesh.pulsefit.ui.profile.utils

fun Float.formatValue(): String {
    return if (this % 1f == 0f) {
        this.toInt().toString()
    } else {
        "%.1f".format(this)
    }
}