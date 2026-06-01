package com.example.yandexpractice.ui.utils

object TimeFormatter {
    
    fun formatTrackTime(millis: Int): String {
        if (millis <= 0) return "00:00"
        val totalSeconds = millis / 1_000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}

