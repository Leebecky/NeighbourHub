package com.example.neighbourhub.ui.widgets

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun CustomTopAppBar_Back(title: String, navBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            CustomIconButton(
                onClickFun = navBack,
                icon = Icons.Filled.ArrowBack
            )
        }
    )
}