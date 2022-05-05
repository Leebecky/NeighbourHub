package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Announcement(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val status: String = "",
    val createdBy: String = "",
): Parcelable
