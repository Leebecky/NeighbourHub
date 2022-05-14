package com.example.neighbourhub.models

import android.os.Parcelable
import android.util.Log
import com.example.neighbourhub.utils.DatabaseCollection
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class Bulletin(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var status: String = "",
    var createdBy: String = "",
) : Parcelable {
    companion object {
        private val firestore = Firebase.firestore.collection(DatabaseCollection.BULLETIN)

        // Retrieve bulletin list
        suspend fun getBulletinList(): List<Bulletin> {
            return try {
                val data = firestore.get().await()
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

        // Update/Create Bulletin records
        suspend fun updateBulletin(data: Bulletin): Boolean {
            return try {
                val doc: DocumentReference

                if (data.id != "") {
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
