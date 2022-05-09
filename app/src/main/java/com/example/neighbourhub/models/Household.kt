package com.example.neighbourhub.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Household(
    val id: String = "",
    val address: AddressLocation = AddressLocation(),
    val residentsList: List<Users> = emptyList(),
    val invitationCode: String = "",
    val membershipStatus: String = ""
): Parcelable
