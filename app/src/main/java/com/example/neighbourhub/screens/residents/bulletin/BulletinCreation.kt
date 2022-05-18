package com.example.neighbourhub.screens.residents.bulletin

import android.content.ContentUris
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.viewmodel.BulletinCreationViewModel
import kotlinx.coroutines.launch

//Reference: https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images

@Composable
fun BulletinCreation(
    navBack: () -> Unit,
    id: String = "",
    vm: BulletinCreationViewModel = viewModel()
) {
    // Local state/variables
    val displayMode =
        if (isSystemInDarkTheme()) R.drawable.ic_baseline_image_search_24 else R.drawable.ic_baseline_image_search_24_dark
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var loaderState by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showImgErrorDialog by remember { mutableStateOf(false) }
    var showUserErrorDialog by remember { mutableStateOf(false) }
    val editable = (vm.createdBy == Users.currentUserId || vm.id == "-1")

    // Loading Image from Gallery
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        vm.url = uri.toString()
    }
    val painter =
        rememberImagePainter(if (vm.url == "") displayMode else vm.url)

    // Content
    Scaffold(
        topBar = { CustomTopAppBar_Back(title = "Bulletin Details", navBack = navBack) }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            // Image Upload
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(enabled = editable) {
                        launcher.launch("image/*")
                        vm.hasImg = false
                    },
                painter = painter,
                contentDescription = "${vm.title} Image",
            )

            if (editable) {
                // Click to Remove Image
                CustomButton(
                    btnText = "Remove Image",
                    btnColor = Color.Red,
                    onClickFun = { vm.url = "" },
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                )
            }

            CustomOutlinedTextField( //Title
                labelText = "Title",
                textValue = vm.title,
                onValueChangeFun = { vm.title = it },
                isSingleLine = true,
                isEnabled = editable
            )

            CustomOutlinedTextField( //Description
                labelText = "Description",
                textValue = vm.desc,
                onValueChangeFun = { vm.desc = it },
                isEnabled = editable
            )

            // Only records belonging to the user can be edited
            // TODO: Or Committee
            if (editable) {
                CustomButtonLoader(
                    btnText = "Save",
                    showLoader = loaderState,
                    onClickFun = {
                        loaderState = true
                        //TODO: Validation
                        //TODO: NO PHOTO = CRASH
                        scope.launch {

                            if (Users.currentUserId != "") { // Checking user id

                                var imgUrl = vm.url
                                if (vm.url != "" && !vm.hasImg) {
                                    // Uploading Image to Firebase Storage
                                    // if an image has been selected and its not an existing image
                                    imgUrl = vm.uploadImage(vm.url, vm.title)
                                }

                                if (imgUrl != "-1") { // Verifying upload
                                    // Updating/Creating Record
                                    val status = vm.updateBulletin(Users.currentUserId, imgUrl)
                                    if (status) {
                                        //TODO: SNACKBAR - Data Saved
                                        navBack()
                                    } else {
                                        showErrorDialog = true
                                        loaderState = false
                                    }
                                } else {
                                    showImgErrorDialog = true
                                    loaderState = false
                                }
                            } else {
                                showUserErrorDialog = true
                                loaderState = false
                            }
                        }

                    }
                )
            }

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

            if (showImgErrorDialog) {
                CustomDialog(
                    alertTitle = "NeighbourHub",
                    alertBody = "Upload error. Please select an image and try again",
                    onDismissFun = { showImgErrorDialog = false })
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun BulletinCreationPreview() {
    NeighbourHubTheme {
        Surface {
            BulletinCreation({}, BulletinCreationViewModel())
        }
    }
}
*/
