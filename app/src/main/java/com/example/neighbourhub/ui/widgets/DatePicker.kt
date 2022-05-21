package com.example.neighbourhub.ui.widgets

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.neighbourhub.utils.DateTimeManager
import java.util.*


//Reference: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
@Composable
fun CustomDatePicker(
    context: Context,
    initYear: Int,
    initMonth: Int,
    initDay: Int,
    entryDate: MutableState<String>
) {

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year: Int, month: Int, day: Int ->
            entryDate.value = "$year-${
                if (month < 10)
                    "0${month + 1}"
                else month + 1
            }-$day"
        }, initYear, initMonth, initDay
    )

    // Setting date limit
    val calendar = DateTimeManager().calendar
    datePickerDialog.datePicker.setMinDate(calendar.timeInMillis)

    // Max Date = +1 year ahead
    calendar.add(Calendar.YEAR, 1)
    datePickerDialog.datePicker.setMaxDate(calendar.timeInMillis)

    CustomIconButton(onClickFun = { datePickerDialog.show() }, icon = Icons.Filled.Today)
}
