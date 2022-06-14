package com.example.neighbourhub.models

import android.os.Parcelable
import android.util.Log
import com.example.neighbourhub.utils.DatabaseCollection
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResidentAssociation(
    var id: String = "",
    var raName: String = "",
    var invitationCode: String = "",
    var address: AddressLocation = AddressLocation(),
    var committeeMemberList: List<String> = emptyList(),
    var householdList: List<String> = emptyList()
) : Parcelable {
    companion object {
        //Retrieve the RA collection from Firestore
        private val firestore = Firebase.firestore.collection(DatabaseCollection.RA)

        // Find the RA based on Invitation Code
        suspend fun findRaByInvitationCode(invCode: String): ResidentAssociation? {
            val data = firestore.whereEqualTo("invitationCode", invCode).get().await()
            return if (data.count() > 0) {
                data.documents[0].toObject<ResidentAssociation>()
            } else {
                null
            }
        }

        // Retrieve ra record
        suspend fun getRaRecord(id: String): ResidentAssociation? {
            return try {
                val data = firestore.document(id).get().await()

                return if (data != null) {
                    data.toObject<ResidentAssociation>()
                } else {
                    ResidentAssociation()
                }

            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
                ResidentAssociation()
            }
        }

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
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
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

        //Get All Residents in Residential Area
        suspend fun retrieveResidentsList(ra: String): List<Users> {
            return try {
                val data = Users.firestore.whereEqualTo("residentsAssociationId", ra).get().await()
                if (!data.isEmpty) {
                    data.toObjects()
                } else {
                    emptyList()
                }
            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
                emptyList()
            }
        }


    }
}