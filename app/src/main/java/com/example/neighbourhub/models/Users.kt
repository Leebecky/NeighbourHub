package com.example.neighbourhub.models

import android.os.Parcelable
import android.util.Log
import com.example.neighbourhub.utils.DatabaseCollection
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class Users(
    val id: String = "",
    val name: String = "",
    val userRole: String = "",
    val address: AddressLocation = AddressLocation(),
    val contactNumber: String = "",
    val residentsAssociationId: String = "",
    val email: String = "",
    val age: Int = 0,
    val dateJoined: String = "",
    val status: String = ""
) : Parcelable {
    companion object {
        private val firebase = FirebaseFirestore.getInstance().collection(DatabaseCollection.USER)

        //? Retrieve the User Profile from the database
        fun getCurrentUser(userId: String): Users? {
            try {
                val data = firebase.document(userId).get()
                val currentUser: Users? = data.result.toObject<Users>()
                return currentUser
            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                return null
            }
        }

        // Update User Profile
        fun updateUserProfile(userId: String, data: Users) {
            try {
                firebase.document(userId).set(data)
            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
            }
        }
    }
}
