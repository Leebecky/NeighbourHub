package com.example.neighbourhub.screens.committee

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Visitor
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.VisitorLogViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun VisitorLog(navBack: () -> Unit, vm: VisitorLogViewModel = viewModel()) {
    //State Variables
    val lazyColumnState = rememberLazyListState()
    val isRefreshing by vm.isRefreshing.collectAsState()
    val visitorLog by vm.visitorLog.collectAsState()

    Scaffold(
        topBar = { CustomTopAppBar_Back(title = stringResource(R.string.visitor_log_title), navBack = navBack) }
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { vm.onRefresh() }) {

            LazyColumn(
                state = lazyColumnState, horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(visitorLog) { item ->
                    VisitorItem(data = item)
                }
            }
        }
    }
}

@Composable
fun VisitorItem(data: Visitor) {
    Card(modifier = Modifier.fillMaxWidth(0.8f)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,    modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.h6
                )
            }
            Divider(color = MaterialTheme.colors.secondary)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = data.visitDate,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = data.entryTime,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.ic_field),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(0.3f)
                )
                Text(
                    text = data.icNumber,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.car_display),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(0.3f)
                )
                Text(
                    text = data.carNumber,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.visiting),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(0.3f)
                )
                Text(
                    text = "${data.addressVisited.houseNumber}, ${data.addressVisited.street}",
                    style = MaterialTheme.typography.subtitle1
                )
            }

        }
    }
}