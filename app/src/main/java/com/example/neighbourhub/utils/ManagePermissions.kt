package com.example.neighbourhub.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


@ExperimentalPermissionsApi
@Composable
private fun RequestReadExternalStorage() {

    // Permission state
    val permissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    Surface {

        when (permissionState.status) {
            // If the  permission is granted, then show screen with the feature enabled
            PermissionStatus.Granted -> {
                Text("Permission Granted")
            }
            is PermissionStatus.Denied -> {
                Column {
                    val textToShow =
                        if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                            // If the user has denied the permission but the rationale can be shown,
                            // then gently explain why the app requires this permission
                            "Access to gallery required to upload images"
                        } else {
                            // If it's the first time the user lands on this feature, or the user
                            // doesn't want to be asked again for this permission, explain that the
                            // permission is required
                            "Access to gallery required to upload images. " +
                                    "Please grant the permission"
                        }
                    Text(textToShow)
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun requestStoragePermission(
    onDismissFun: () -> Unit
) {
    Dialog(onDismissRequest = onDismissFun, content = { RequestReadExternalStorage() })
}