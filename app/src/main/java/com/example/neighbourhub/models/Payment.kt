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
data class Payment(
    var id: String = "",
    var createdBy: String = "",
    var recipient: Recipient = Recipient(),
    var status: String = "",
    var dateline: String = "",
    var description: String = "",
    var amount: Double = 0.0,
    var raId:String = ""
) : Parcelable {
    companion object {
        private val firestore = Firebase.firestore.collection(DatabaseCollection.PAYMENT)

        // Retrieve bulletin list by resident association
        suspend fun getPaymentList(ra: String): List<Payment> {
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
        suspend fun getPaymentRecord(id: String): Payment? {
            return try {
                val data = firestore.document(id).get().await()

                return if (data != null) {
                    data.toObject<Payment>()
                } else {
                    Payment()
                }

            } catch (ex: Exception) {
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                Payment()
            }
        }


        // Update/Create Payment records
        suspend fun updatePayment(data: Payment): Boolean {
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
                Log.println(Log.INFO, "Test", ex.message.orEmpty())
                false
            }
        }
    }
}
