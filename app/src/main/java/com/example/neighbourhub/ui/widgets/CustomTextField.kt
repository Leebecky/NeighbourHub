package com.example.neighbourhub.ui.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.screens.Login
import com.example.neighbourhub.ui.theme.NeighbourHubTheme

@Composable
fun CustomOutlinedTextField(
    placeholder: String,
    textValue: String,
    onValueChangeFun: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChangeFun,
        label = { Text(text = placeholder) },
        singleLine = isSingleLine,
        modifier = modifier
    )
}

@Composable
fun CustomTextField(
    placeholder: String,
    textValue: String,
    onValueChangeFun: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSingleLine:Boolean = false
) {
    TextField(
        value = textValue,
        onValueChange = onValueChangeFun,
        label = { Text(text = placeholder) },
        singleLine = isSingleLine,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CustomOutlinedTextFieldPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CustomOutlinedTextField("test", "test", {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CustomTextField("test", "test", {})
        }
    }
}