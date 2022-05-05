package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Facility(
    val id: String = "",
    val name: String = "",
    val status: String = "",
    val capacity: Int = 0
): Parcelable
