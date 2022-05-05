package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val quantity: Int = 0,
    val imageUrl: String = "",
    val productType: String = "",
    val status: String = "",
    val createdBy: String = ""
): Parcelable
