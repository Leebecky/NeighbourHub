package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressLocation(
    var houseNumber: String = "",
    var street: String = "",
    var residentialArea: String = "",
    var state: String = "",
    var postcode: String = ""
) : Parcelable
