package com.example.neighbourhub.screens.residents.bulletin

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Bulletin
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.viewmodel.BulletinBoardViewModel

@Composable
fun BulletinBoard(
    padding: PaddingValues,
    navCreation: () -> Unit,
    vm: BulletinBoardViewModel = viewModel()
) {
    val bulletinList by vm.bulletinList.collectAsState()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {

        // Display Bulletin
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            items(bulletinList) { item ->
                Announcement(item)
            }
        }

        // Add New Bulletin
        FloatingActionButton(onClick = navCreation,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 10.dp, bottom = 10.dp),
            content = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                )
            })
    }

}

@Composable
fun Announcement(item: Bulletin) {
    val placeholderImg =
        if (isSystemInDarkTheme()) R.drawable.ic_baseline_image_search_24 else R.drawable.ic_baseline_image_search_24_dark

    Card(
        shape = AbsoluteCutCornerShape(topLeft = 20.dp),
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(0.8f)
    ) {
        Column() {

            Image(
                modifier = Modifier
//                    .wrapContentSize()
                    .clip(CutCornerShape(topStart = 20.dp)),
                contentScale = ContentScale.Fit,
                painter = rememberImagePainter(
                    data = item.imageUrl,
                    builder = {
                        size(OriginalSize)
                        error(placeholderImg)
                    }
                ),
                contentDescription = "${item.title} Image",
            )
            Text(text = item.title, style = MaterialTheme.typography.h6)
            Text(
                text = item.description,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun BulletinBoardPreview() {
    NeighbourHubTheme {
        Announcement(
            Bulletin(
                title = "Hari Raya Open House",
                description = "Raya open house this Sunday! Mari berinteraksi dan makan makan! Potluck!",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZkCMrHiaOmWdLc0YFx9dThod_LDTO9RIMyw&usqp=CAU"
            )
        )
//        Surface {
//            BulletinBoard({}, BulletinBoardViewModel())
//        }
    }
}
