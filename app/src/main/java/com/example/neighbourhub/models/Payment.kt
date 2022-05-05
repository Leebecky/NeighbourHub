package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val id: String = "",
    val createdBy: String = "",
    val recipient: String = "",
    val status: String = "",
    val datePaid: String = "",
    val description: String = "",
    val amount: Double = 0.0
) : Parcelable
