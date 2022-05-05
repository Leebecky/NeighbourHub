package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Visitor(
    val id: String = "",
    val name: String = "",
    val status: String = "",
    val icNumber: String = "",
    val carNumber: String = "",
    val createdBy: String = "",
    val expectedEntryTime: String = "",
    val entryTime: String = "",
    val addressVisited: AddressLocation = AddressLocation()
) : Parcelable