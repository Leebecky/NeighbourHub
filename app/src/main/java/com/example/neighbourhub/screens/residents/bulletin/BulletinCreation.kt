package com.example.neighbourhub.screens.residents.bulletin

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomButtonLoader
import com.example.neighbourhub.ui.widgets.CustomDialog
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.BulletinCreationViewModel
import kotlinx.coroutines.launch

@Composable
fun BulletinCreation(
    navBack: () -> Unit,
    user: Users?,
    vm: BulletinCreationViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()
    var loaderState by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showUserErrorDialog by remember { mutableStateOf(false) }

    var currentUser: Users? = null
    LaunchedEffect(key1 = Unit) {
        currentUser = Users.currentUserId?.let { Users.getCurrentUser(it) }
    }

    Scaffold(
        topBar = { CustomTopAppBar_Back(title = "New Bulletin", navBack = navBack) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            CustomOutlinedTextField( //Title
                labelText = "Title",
                textValue = vm.title,
                onValueChangeFun = { vm.title = it },
                isSingleLine = true
            )

            CustomOutlinedTextField( //Description
                labelText = "Description",
                textValue = vm.desc,
                onValueChangeFun = { vm.desc = it })

            //    ToDO: Image Upload
            CustomOutlinedTextField( //Placeholder
                labelText = "Placeholder",
                textValue = "Image Uploader",
                onValueChangeFun = { })

            CustomButtonLoader(
                btnText = "Save",
                showLoader = loaderState,
                onClickFun = {
                    loaderState = true
                         if (user != null) {
                             Log.println(Log.INFO, "Test", user.id)
                         }
                        currentUser?.let { it1 -> Log.println(Log.INFO, "Test", it1.id) }
                    scope.launch {

                        if (currentUser != null) {
                            val status = vm.UpdateBulletin(currentUser)
                            if (status) {
                                //TODO: SNACKBAR - Data Saved
                            } else {
                                showErrorDialog = true
                            }
                        } else {
                            showUserErrorDialog = true
                        }
                    }
                    loaderState = false
                }
            )

            // ERROR ALERT DIALOG
            if (showErrorDialog) {
                CustomDialog(
                    alertTitle = "NeighbourHub",
                    alertBody = "Unexpected error encountered. Please try again",
                    onDismissFun = { showErrorDialog = false })
            }

            if (showUserErrorDialog) {
                CustomDialog(
                    alertTitle = "NeighbourHub",
                    alertBody = "Authentication error. Please login and try again",
                    onDismissFun = { showUserErrorDialog = false })
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun BulletinCreationPreview() {
    NeighbourHubTheme {
        Surface {
            BulletinCreation({}, Users(), BulletinCreationViewModel())
        }
    }
}
