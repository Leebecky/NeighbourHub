package com.example.neighbourhub.models

import android.os.Parcelable
import android.util.Log
import com.example.neighbourhub.utils.Constants
import com.example.neighbourhub.utils.DatabaseCollection
import com.example.neighbourhub.utils.DateTimeManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val id: String = "",
    var name: String = "",
    var userRole: String = "",
    var address: AddressLocation = AddressLocation(),
    var contactNumber: String = "",
    var residentsAssociationId: String = "",
    var email: String = "",
    var age: Int = 0,
    var dateJoined: String = "",
    var status: String = ""
) : Parcelable {
    companion object {
        val firestore = Firebase.firestore.collection(DatabaseCollection.USERS)
        var currentUserId = Firebase.auth.currentUser?.uid

        fun updateLoginUser() {
            currentUserId = Firebase.auth.currentUser?.uid
        }

        //? Retrieve the User Profile from the database
        suspend fun getCurrentUser(userId: String): Users? {
            try {
                val data = firestore.document(userId).get().await()
                val currentUser: Users? = data.toObject<Users>()
                return currentUser
            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
                return null
            }
        }

        // Update User Profile
        suspend fun updateUserProfile(data: Users): Boolean {
            return try {
                firestore.document(data.id).set(data).await()
                true
            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
                false
            }
        }

        // New User Registration
        suspend fun registerUser(userId: String, email: String) {
            try {

                val data: Users = Users(
                    id = userId,
                    userRole = Constants.ResidentRole,
                    dateJoined = DateTimeManager().now("yyyy-MM-dd"),
                    email = email,
                    status = Constants.PendingStatus
                )

                updateUserProfile(data)
            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
            }
        }
    }
}
