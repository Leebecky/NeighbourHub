package com.example.neighbourhub.screens.residents

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.neighbourhub.models.Visitor
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTextButton

@Composable
fun VisitorRegistered(navHome: () -> Unit, data: Visitor, qrCode: Bitmap?) {

    // Composable State variables
    val scrollState = rememberScrollState()

    //Content
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            if (qrCode != null) {
                Image(
                    bitmap = qrCode.asImageBitmap(), contentDescription = "Visitor QR",
                    modifier = Modifier.size(150.dp)
                )
            }

            CustomOutlinedTextField( //Visitor Name
                labelText = "Name",
                textValue = data.name,
                isReadOnly = true,
                onValueChangeFun = {},
                modifier = Modifier.padding(top = 16.dp)
            )

            CustomOutlinedTextField( //Visitor IC Number
                labelText = "IC Number",
                textValue = data.icNumber,
                isReadOnly = true,
                onValueChangeFun = {},
                modifier = Modifier.padding(top = 4.dp)
            )

            CustomOutlinedTextField( //Visitor Car Number
                labelText = "Car Number",
                textValue = data.carNumber,
                isReadOnly = true,
                onValueChangeFun = {},
                modifier = Modifier.padding(top = 4.dp)
            )


            CustomOutlinedTextField(
                //Visitor Visitation Date
                labelText = "Visitation Date",
                textValue = data.visitDate,
                isReadOnly = true,
                onValueChangeFun = {},
                modifier = Modifier.padding(top = 4.dp),
            )

            CustomOutlinedTextField( //Visitor Expected Entry Time
                labelText = "Entry Time",
                textValue = data.expectedEntryTime,
                isReadOnly = true,
                onValueChangeFun = {},
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Show this QR Code at the guardhouse to skip registration",
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
            )
            Text(
                text = "- This page will not be displayed again! -",
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
            )

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                CustomTextButton(
                    btnText = "CLOSE",
                    onClickFun = navHome,
                    btnTextColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun VisitorRegistrationPreview() {
    NeighbourHubTheme {
        Surface {
            VisitorRegistration({}, VisitorRegistrationViewModel())
        }
    }
}
*/
