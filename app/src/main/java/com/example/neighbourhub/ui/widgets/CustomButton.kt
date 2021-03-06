package com.example.neighbourhub.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomButton(
    btnText: String,
    onClickFun: () -> Unit,
    modifier: Modifier = Modifier,
    btnColor: Color = MaterialTheme.colors.primary,
    btnTextColor: Color = MaterialTheme.colors.onPrimary,
) {
    Button(
        onClick = onClickFun,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = btnColor,
            contentColor = btnTextColor
        ),
        modifier = modifier
            .width(150.dp)
            .height(50.dp),
    ) {
        Text(btnText, fontSize = 20.sp)
    }
}

@Composable
fun CustomIconButton(onClickFun: () -> Unit, icon: ImageVector, btnDescription: String = "") {
    IconButton(onClick = onClickFun) {
        Icon(
            imageVector = icon,
            contentDescription = btnDescription
        )
    }
}

@Composable
fun CustomTextButton(
    btnText: String,
    onClickFun: () -> Unit,
    fontSize: TextUnit = MaterialTheme.typography.button.fontSize,
    modifier: Modifier = Modifier,
    btnColor: Color = MaterialTheme.colors.primary,
    btnTextColor: Color = MaterialTheme.colors.onPrimary,
) {
    TextButton(
        onClick = onClickFun,
        modifier = modifier
    ) {
        Text(btnText, fontSize = fontSize, color = btnTextColor)
    }
}

@Composable
fun CustomButtonLoader(
    btnText: String,
    onClickFun: () -> Unit,
    showLoader: Boolean,
    modifier: Modifier = Modifier,
    btnColor: Color = MaterialTheme.colors.primary,
    btnTextColor: Color = MaterialTheme.colors.onPrimary,
    fontSize: TextUnit = 20.sp,
) {
    TextButton(
        onClick = onClickFun,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = btnColor,
            contentColor = btnTextColor
        ),
        modifier = modifier
            .width(150.dp)
            .height(50.dp),
    ) {
        if (showLoader) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(text = btnText, fontSize = fontSize)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    Column {
        CustomButtonLoader("Test", {}, showLoader = false)
        CustomButtonLoader("Test", {}, showLoader = true)
    }
}