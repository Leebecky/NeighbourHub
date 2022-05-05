package com.example.neighbourhub.ui.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomButton(
    btnText: String,
    onClickFun: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickFun,
       shape = RoundedCornerShape(50.dp),
        modifier = modifier
            .width(150.dp)
            .height(50.dp)

    ) {
        Text(btnText, fontSize = 20.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CustomButton("Test", {})
}