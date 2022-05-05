package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressLocation(
    val houseNumber: String = "",
    val street: String = "",
    val residentialArea: String = "",
    val state: String = "",
    val postcode: String = ""
) : Parcelable
