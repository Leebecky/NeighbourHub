package com.example.neighbourhub.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.neighbourhub.models.ResidentAssociation
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.*
import com.google.firebase.ktx.Firebase

class RaInvitationViewModel() : ViewModel() {
    var invCode by mutableStateOf("")

    // Update the User Record with the Resident Association Id
    suspend fun setRaForUser(): Boolean {
        val ra: ResidentAssociation? = ResidentAssociation.findRaByInvitationCode(invCode)

        return if (ra != null) {
            val user: Users? = Firebase.auth.currentUser?.let { Users.getCurrentUser(it.uid) }

            return if (user != null) {
                user.residentsAssociationId = ra.id
                user.address.postcode = ra.address.postcode
                user.address.state = ra.address.state
                user.address.residentialArea = ra.address.residentialArea
                Users.updateUserProfile(user)
                true
            } else { //TODO: Error management
                Log.println(Log.INFO, "Test", "Login error")
                false
            }
        } else {
            false
        }
    }
}
