package com.example.neighbourhub.utils

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

suspend fun uploadImageCommon(filePath: String, fileName:String): String {
    val db = Firebase.storage.reference
//        val file = Uri.fromFile(File(filePath))
    val dbPath = db.child("images/$fileName")
    val uploadTask = dbPath.putFile(Uri.parse(filePath)).await()

    if (uploadTask?.task?.isSuccessful == true) {
        return uploadTask.storage.downloadUrl.await().toString()
    }
    return "-1"
}