package com.example.neighbourhub.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.neighbourhub.models.Bulletin
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

class BulletinCreationViewModel() : ViewModel() {
    var title by mutableStateOf("")
    var desc by mutableStateOf("")
    var url by mutableStateOf("")
    var status by mutableStateOf("")
    var createdBy by mutableStateOf("")

    suspend fun UpdateBulletin(userId: String?, imgUrl: String): Boolean {

        val post: Bulletin = Bulletin(
            title = title,
            description = desc,
            imageUrl = imgUrl,
            status = Constants.ActiveStatus,
            createdBy = userId.orEmpty()
        )
        return Bulletin.updateBulletin(post)

    }

    suspend fun UploadImage(filePath: String): String {
        val db = Firebase.storage.reference
//        val file = Uri.fromFile(File(filePath))
        val dbPath = db.child("images/test")
        val uploadTask = dbPath.putFile(Uri.parse(filePath)).await()

        if (uploadTask?.task?.isSuccessful == true) {
            return uploadTask.storage.downloadUrl.await().toString()
        }
        return ""
    }
}