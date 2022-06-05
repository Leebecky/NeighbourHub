package com.example.neighbourhub.screens.committee

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.models.Payment
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.ManagePaymentViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
enum class ExpandableState { VISIBLE, HIDDEN }
@Composable
fun ManagePayment(
    navCreation: (id: String) -> Unit,
    navBack: () -> Unit,
    vm: ManagePaymentViewModel = viewModel()
) {

    val payList by vm.payList.collectAsState()
    val isRefreshing by vm.isRefreshing.collectAsState()
    val listState = rememberLazyListState()
    Scaffold(
        topBar = {
            CustomTopAppBar_Back(title = "Manage Payments", navBack = navBack)
        }) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { vm.refresh() },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Display Payment Orders
                LazyColumn(
                    state = listState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(payList) { item ->
                        ExpandableContainer(item.description, content = { PaymentItem(item) })
                    }
                }

                // Add New Payment Order
                FloatingActionButton(onClick = { navCreation("-1") },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 10.dp, bottom = 10.dp),
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Payment Request",
                        )
                    })
            }
        }
    }
}



@Composable
fun ExpandableContainer(
    title: String,
    defaultState: ExpandableState = ExpandableState.VISIBLE,
    content: @Composable ColumnScope.() -> Unit,
) {

    //State
    var isContentVisible by rememberSaveable { mutableStateOf(defaultState) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray)
            .clickable {
                isContentVisible =
                    if (isContentVisible == ExpandableState.VISIBLE) ExpandableState.HIDDEN else ExpandableState.VISIBLE
            },
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)

        AnimatedVisibility(visible = isContentVisible == ExpandableState.VISIBLE) {
            Column {
              Text("This is expansion")
            }
        }
    }

}

@Composable
fun PaymentItem(item: Payment) {
    Card(
        shape = AbsoluteCutCornerShape(topLeft = 20.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable {

            }
    ) {
        Column {
            Text(
                text = item.dateline,
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
            Text(
                text = item.amount.toString(),
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
//@Composable
//fun PaymentBoardPreview() {
//    NeighbourHubTheme {
//        PaymentItem(
//            Payment(
//                title = "Hari Raya Open House",
//                description = "Raya open house this Sunday! Mari berinteraksi dan makan makan! Potluck!",
//                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZkCMrHiaOmWdLc0YFx9dThod_LDTO9RIMyw&usqp=CAU"
//            ), {}
//        )
////        Surface {
////            PaymentBoard({}, PaymentBoardViewModel())
////        }
//    }
//}
