package com.example.neighbourhub.models

import android.location.Address
import android.os.Parcelable

data class ResidentAssociation(
    val id: String = "",
    val residentialAreaName: String = "",
    val invitationCode:String = "",
    val address: AddressLocation,
    val committeeMemberList: List<User> = emptyList(),
    val householdList: List<Household> = emptyList()
    ): Parcelable
