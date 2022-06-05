package com.example.neighbourhub.screens.residents.bulletin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.utils.Constants
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
    val scaffoldState = rememberScaffoldState()

    var loaderState by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showImgErrorDialog by remember { mutableStateOf(false) }
    var showUserErrorDialog by remember { mutableStateOf(false) }
    val currentUser = vm.currentUser.collectAsState()
    val editable =
        (vm.createdBy == Users.currentUserId || vm.id == "-1" || currentUser.value.userRole == Constants.CommitteeRole)

    // Textfield Error State
    var titleError by rememberSaveable { mutableStateOf(false) }
    var descError by rememberSaveable { mutableStateOf(false) }


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
        scaffoldState = scaffoldState,
        topBar = { CustomTopAppBar_Back(title = "Bulletin Details", navBack = navBack) },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
//                .fillMaxWidth(0.8f)
                .verticalScroll(scrollState),
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
                        .height(40.dp)
                )
            }

            CustomOutlinedTextField( //Title
                labelText = "Title",
                textValue = vm.title,
                onValueChangeFun = {
                    vm.title = it
                    if (titleError) {
                        titleError = false
                    }
                },
                isSingleLine = true,
                isEnabled = editable,
                errorState = titleError,
                errorMsg = "Required field!"
            )

            CustomOutlinedTextField(
                //Description
                labelText = "Description",
                textValue = vm.desc,
                maxLines = 3,
                onValueChangeFun = {
                    vm.desc = it
                    if (descError) {
                        descError = false
                    }
                },
                isEnabled = editable,
                errorState = descError,
                errorMsg = "Required field!"
            )

            // Only records belonging to the user can be edited
            if (editable) {
                CustomButtonLoader(
                    btnText = "Save",
                    showLoader = loaderState,
                    onClickFun = {
                        loaderState = true

                        scope.launch {

                            // Input validation - Checking Title Content
                            if (vm.title.isEmpty()) {
                                titleError = true
                                loaderState = false

                                // Display Snackbar
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Please fill in all required fields",
                                    //   actionLabel = "Do something."
                                )
                                return@launch
                            } else {
                                titleError = false
                            }

                            // Input validation - Checking Description Content
                            if (vm.desc.isEmpty()) {
                                descError = true
                                loaderState = false

                                // Display Snackbar
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Please fill in all required fields"
                                )
                                return@launch
                            } else {
                                descError = false
                            }

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
                                        // Display Snackbar
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Bulletin has been saved. Please refresh the Bulletin page"
                                        )
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
                    })
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
