package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.Bulletin
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import com.example.neighbourhub.utils.uploadImageCommon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BulletinCreationViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //    private val id: String? = savedStateHandle.get("id")
    val id = savedStateHandle.get<String>("id").orEmpty()

    var title by mutableStateOf("")
    var desc by mutableStateOf("")
    var url by mutableStateOf("")
    var status by mutableStateOf("")
    var hasImg by mutableStateOf(false)
    var createdBy by mutableStateOf("")

    private val _currentUser = MutableStateFlow(Users())
    val currentUser: StateFlow<Users>
        get() = _currentUser
    private var ra = currentUser.value.residentsAssociationId

    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            ra = currentUser.value.residentsAssociationId

            if (id != "-1" && id != "") {
                val record = Bulletin.getBulletinRecord(id)

                if (record != null) {
                    title = record.title
                    desc = record.description
                    url = record.imageUrl
                    status = record.status
                    createdBy = record.createdBy

                    if (record.imageUrl != "") {
                        hasImg = true
                    }
                }
            }
        }
    }

    suspend fun updateBulletin(userId: String?, imgUrl: String): Boolean {

        val post = Bulletin(
            id = id,
            title = title,
            description = desc,
            imageUrl = imgUrl,
            status = Constants.ActiveStatus,
            createdBy = userId.orEmpty(),
            raId = ra
        )
        return Bulletin.updateBulletin(post)
    }

    suspend fun uploadImage(filePath: String, fileName: String): String {
        return uploadImageCommon(filePath, fileName)
    }

    suspend fun deleteBulletin(id:String):Boolean {
        return Bulletin.deleteBulletin(id)
    }
}