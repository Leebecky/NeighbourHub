package com.example.neighbourhub.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResidentAssociation(
    val id: String = "",
    val residentialAreaName: String = "",
    val invitationCode:String = "",
    val address: AddressLocation = AddressLocation(),
    val committeeMemberList: List<User> = emptyList(),
    val householdList: List<Household> = emptyList()
    ): Parcelable
