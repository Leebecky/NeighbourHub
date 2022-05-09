package com.example.neighbourhub.models

import android.os.Parcelable
import android.util.Log
import com.example.neighbourhub.utils.DatabaseCollection
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class ResidentAssociation(
    var id: String = "",
    var raName: String = "",
    var invitationCode: String = "",
    var address: AddressLocation = AddressLocation(),
    var committeeMemberList: List<Users> = emptyList(),
    var householdList: List<Household> = emptyList()
) : Parcelable {
    companion object {
        //Retrieve the RA collection from Firestore
        private val firestore = FirebaseFirestore.getInstance().collection(DatabaseCollection.RA)

        // Create/Update the corresponding record
        fun updateResidentAssociation(data: ResidentAssociation): Boolean {
            return try {
                val doc: DocumentReference

                if (data.id != "") {
                    doc = firestore.document(data.id)
                } else {
                    doc = firestore.document()
                    data.id = doc.id
                }

                doc.set(data)

                true
            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                return false
            }
        }

        //Generate Invitation Code
        fun generateInvCode(): String {
            //Reference: https://www.baeldung.com/kotlin/random-alphanumeric-string
            val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            val invCode = (1..5)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
            return invCode
        }
    }
}