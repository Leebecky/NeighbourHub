package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.neighbourhub.models.AddressLocation
import com.example.neighbourhub.models.ResidentAssociation
class RaCreationViewModel : ViewModel() {
    // Variables
    var residentialName by mutableStateOf("")
    var residentialArea by mutableStateOf("")
    var addPostcode by mutableStateOf("")
    var addState by mutableStateOf("")

    val stateList: List<String> = listOf(
        "Johor",
        "Kedah",
        "Kelantan",
        "Malacca",
        "Negeri Sembilan",
        "Pahang",
        "Penang",
        "Perak",
        "Sabah",
        "Sarawak",
        "Selangor",
        "Terengganu",
        "Kuala Lumpur",
        "Putrajaya",
        "Labuan"
    )

    fun registerAssociation() {
        val invCode = ResidentAssociation.generateInvCode()
        val ra:ResidentAssociation = ResidentAssociation(raName = residentialName, invitationCode = invCode, address = AddressLocation(postcode = addPostcode, state = addState, residentialArea = residentialArea))

        ResidentAssociation.updateResidentAssociation(ra)
    }
}