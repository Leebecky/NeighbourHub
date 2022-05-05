package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val remark: String = "",
    val status: String = "",
    val dateOrdered: String = "",
    val timeOrdered: String = "",
    val dateDelivered: String = "",
    val timeDelivered: String = "",
): Parcelable
