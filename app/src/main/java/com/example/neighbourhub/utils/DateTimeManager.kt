package com.example.neighbourhub.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeManager {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    fun now(formatString: String): String {
        val dateFormatter: SimpleDateFormat = SimpleDateFormat(formatString)
        return dateFormatter.format(calendar.time)
    }
}