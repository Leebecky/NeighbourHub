package com.example.neighbourhub.ui.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.neighbourhub.screens.setup.RaCreation
import com.example.neighbourhub.ui.theme.NeighbourHubTheme

@Composable
fun CustomDialog(
    alertTitle: String,
    alertBody: String,
    onDismissFun: () -> Unit,
    customBtn: @Composable () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismissFun,
        title = { Text(alertTitle, fontWeight = FontWeight.ExtraBold) },
        text = { Text(text = alertBody) },
        buttons = customBtn,
    )
}

@Composable
fun CustomDialogBasic(
    alertTitle: String,
    alertBody: String,
    onDismissFun: () -> Unit,
    btnCancelClick: () -> Unit,
    btnAcceptClick: () -> Unit,
    modifier: Modifier = Modifier,
    btnAcceptText: String = "ACCEPT",
    btnCancelText: String = "CANCEL"
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissFun,
        title = { Text(alertTitle, fontWeight = FontWeight.ExtraBold) },
        text = { Text(text = alertBody) },
        buttons = {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                CustomTextButton(
                    btnText = btnCancelText,
                    onClickFun = btnCancelClick,
                    btnTextColor = MaterialTheme.colors.primary
                )
                CustomTextButton(
                    btnText = btnAcceptText,
                    onClickFun = btnAcceptClick,
                    btnTextColor = MaterialTheme.colors.primary
                )
            }
        },
    )
}

@Composable
fun CustomDialogClose(
    alertTitle: String,
    alertBody: String,
    onDismissFun: () -> Unit,
    btnCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    btnCloseText: String = "CLOSE",
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissFun,
        title = { Text(alertTitle, fontWeight = FontWeight.ExtraBold) },
        text = { Text(text = alertBody) },
        buttons = {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                CustomTextButton(
                    btnText = btnCloseText,
                    onClickFun = btnCloseClick,
                    btnTextColor = MaterialTheme.colors.primary
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun CustomDialogPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize(0.3f)) {
            CustomDialogBasic(
                "Title", "Body", {}, {}, {}
            )
        }
    }
}

/*
@Composable
fun DialogTest() {
    Column {
        val openDialog = remember { mutableStateOf(false) }
        CustomButton(btnText = "Test", onClickFun = { openDialog.value = true })
        if (openDialog.value) {
            CustomDialog()
        }
    }
}
*/