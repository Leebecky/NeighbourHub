package com.example.neighbourhub.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import applyTextFieldFixes
import com.example.neighbourhub.R
import com.example.neighbourhub.ui.theme.NeighbourHubTheme

@ExperimentalComposeUiApi
@Composable
fun CustomOutlinedTextField(
    labelText: String,
    textValue: String,
    onValueChangeFun: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    maxLines: Int = 1,
    trailingIcon: @Composable () -> Unit = {},
    errorState: Boolean = false,
    errorMsg: String = stringResource(R.string.custom_outlined_text_field_error_msg_default),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    Column {
        val textFieldValueState by remember {
            mutableStateOf(
                TextFieldValue(text = textValue)
            )
        }
        OutlinedTextField(
            modifier = modifier.applyTextFieldFixes(textFieldValueState),
            value = textValue,
            onValueChange = onValueChangeFun,
//            onValueChange = {textFieldValueState = it},
            label = { Text(text = labelText) },
            singleLine = isSingleLine,
            trailingIcon = trailingIcon,
            enabled = isEnabled,
            readOnly = isReadOnly,
            isError = errorState,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions
        )
        if (errorState) {
            Text(
                text = errorMsg,
                style = MaterialTheme.typography.subtitle2,
                color = Color.Red,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun CustomOutlinedTextFieldPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CustomOutlinedTextField(
                "test",
                "test",
                {},
                errorState = true,
                errorMsg = "Required Field"
            )
        }
    }
}
