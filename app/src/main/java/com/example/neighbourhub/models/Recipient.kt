package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Recipient(
    var residentId: String = "",
    var paymentStatus: String = "",
    var paymentDate:String = ""
) : Parcelable