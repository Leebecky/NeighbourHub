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
data class Bulletin(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var status: String = "",
    var createdBy: String = "",
    var raId: String = ""
) : Parcelable {
    companion object {
        private val firestore = Firebase.firestore.collection(DatabaseCollection.BULLETIN)

        // Retrieve bulletin list by resident association
        suspend fun getBulletinList(ra: String): List<Bulletin> {
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
        suspend fun getBulletinRecord(id: String): Bulletin? {
            return try {
                val data = firestore.document(id).get().await()

                return if (data != null) {
                    data.toObject<Bulletin>()
                } else {
                    Bulletin()
                }

            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                Bulletin()
            }
        }


        // Update/Create Bulletin records
        suspend fun updateBulletin(data: Bulletin): Boolean {
            return try {
                val doc: DocumentReference

                if (data.id != "-1") {
                    doc = firestore.document(data.id)
                } else {
                    doc = firestore.document()
                    data.id = doc.id
                }

                doc.set(data).await()

                true
            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
                false
            }
        }

        // Delete Bulletin Records
        suspend fun deleteBulletin(id: String): Boolean {
            return try {
                val doc = firestore.document(id)
                doc.delete().await()

                true
            } catch (ex: Exception) {
                Log.println(Log.INFO, "NeighbourHub", ex.message.orEmpty())
                false
            }
        }

    }
}
