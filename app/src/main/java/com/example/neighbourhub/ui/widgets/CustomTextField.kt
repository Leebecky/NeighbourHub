package com.example.neighbourhub.ui.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.ui.theme.NeighbourHubTheme

@Composable
fun CustomOutlinedTextField(
    labelText: String,
    textValue: String,
    onValueChangeFun: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
    isEnabled: Boolean = true,
    trailingIcon: @Composable ()-> Unit = {},
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChangeFun,
        label = { Text(text = labelText) },
        singleLine = isSingleLine,
        modifier = modifier,
        trailingIcon = trailingIcon,
        enabled = isEnabled
    )
}

@Composable
fun CustomFilledTextField(
    labelText: String,
    textValue: String,
    onValueChangeFun: (String) -> Unit,
    isSingleLine: Boolean = false,
    trailingIcon: @Composable ()-> Unit = {},
    modifier: Modifier = Modifier
) {
    TextField(
        value = textValue,
        onValueChange = onValueChangeFun,
        label = { Text(text = labelText) },
        singleLine = isSingleLine,
        trailingIcon = trailingIcon,
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
            CustomFilledTextField("test", "test", {})
        }
    }
}