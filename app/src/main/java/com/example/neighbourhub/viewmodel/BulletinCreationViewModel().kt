package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.neighbourhub.models.Bulletin
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants

class BulletinCreationViewModel() : ViewModel() {
    var title by mutableStateOf("")
    var desc by mutableStateOf("")
    var url by mutableStateOf("")
    var status by mutableStateOf("")
    var createdBy by mutableStateOf("")

    suspend fun UpdateBulletin(user: Users?): Boolean {
        if (user != null) {
            val post: Bulletin = Bulletin(
                title = title,
                description = desc,
                imageUrl = url,
                status = Constants.ActiveStatus,
                createdBy = user.id
            )
            return Bulletin.updateBulletin(post)
        }
        return false
    }
}