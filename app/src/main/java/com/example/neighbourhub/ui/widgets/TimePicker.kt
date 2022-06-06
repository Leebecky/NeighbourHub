package com.example.neighbourhub.ui.widgets

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

//Referenced from: https://stackoverflow.com/questions/60417233/jetpack-compose-date-time-picker,Chandler
@Composable
fun CustomTimePicker(
    context: Context,
    initHour: Int,
    initMinute: Int,
    entryTime: MutableState<String>
) {

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            entryTime.value = "$hour:$minute"
        }, initHour, initMinute, false
    )

    CustomIconButton(onClickFun = { timePickerDialog.show() }, icon = Icons.Filled.Schedule)

}