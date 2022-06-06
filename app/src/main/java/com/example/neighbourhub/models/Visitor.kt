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
import java.lang.Exception

@Parcelize
data class Visitor(
    var id: String = "",
    var name: String = "",
    var status: String = "",
    var icNumber: String = "",
    var carNumber: String = "",
    var createdBy: String = "",
    var expectedEntryTime: String = "",
    var entryTime: String = "",
    var visitDate: String = "",
    var raId:String = "",
    var addressVisited: AddressLocation = AddressLocation()
) : Parcelable {
    companion object {
        private val firestore = Firebase.firestore.collection(DatabaseCollection.VISITOR)

        // Retrieve bulletin list by resident association
        suspend fun getVisitorList(ra: String): List<Visitor> {
            return try {

                val data = firestore.whereEqualTo("raId", ra).get().await()
                if (!data.isEmpty) {
                    data.toObjects()
                } else {
                    emptyList()
                }
            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                emptyList()
            }
        }

        // Retrieve bulletin record
        suspend fun getVisitorRecord(id: String): Visitor? {
            return try {
                val data = firestore.document(id).get().await()

                return if (data != null) {
                    data.toObject<Visitor>()
                } else {
                    Visitor()
                }

            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                Visitor()
            }
        }


        // Update/Create Visitor records
        suspend fun updateVisitor(data: Visitor): Boolean {
            return try {
                val doc: DocumentReference

                if (data.id != "" ) {
                    doc = firestore.document(data.id)
                } else {
                    doc = firestore.document()
                    data.id = doc.id
                }

                doc.set(data).await()

                true
            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                false
            }
        }
    }
}