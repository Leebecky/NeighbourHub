package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class User(
    val id:String = "",
    val name:String = "",
    val userRole:String = "",
    val address: AddressLocation = AddressLocation(),
    val contactNumber:String = "",
    val residentsAssociationId:String = "",
    val email:String = "",
    val age: Int = 0,
    val dateJoined: String = "",
    val status: String = ""
): Parcelable
