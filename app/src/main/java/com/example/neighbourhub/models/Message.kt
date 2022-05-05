package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: String = "",
    val sender: String = "",
    val status: String = "",
    val text: String = "",
    val timeSent: String = "",
    val dateSent: String = "",
) : Parcelable
