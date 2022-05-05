package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
data class Booking(
    val id: String = "",
    val facilityId: String = "",
    val date: String = "",
    val time: String = "",
    val numberOfPeople: Int = 0
    ): Parcelable