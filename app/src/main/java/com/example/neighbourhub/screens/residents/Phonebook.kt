package com.example.neighbourhub.screens.residents

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.utils.Constants
import com.example.neighbourhub.viewmodel.PhonebookViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Phonebook(navBack: () -> Unit, vm: PhonebookViewModel = viewModel()) {
    // State Variables
    val lazyColumnState = rememberLazyListState()
//    val contactList by vm.contactList.collectAsState()
    val groupedContacts by vm.groupedContacts.collectAsState()
    val isRefreshing by vm.isRefreshing.collectAsState()

    // Page
    Scaffold(
        topBar = {
            CustomTopAppBar_Back(title = "Phonebook", navBack = { navBack() })
        }
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { vm.onRefresh() }) {

            LazyColumn(
                state = lazyColumnState, horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                groupedContacts.forEach { (alphabet, contactList) ->
                    stickyHeader {
                        ContactHeader(alphabet)
                    }
                    items(contactList) { item ->
                        ContactItem(data = item)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactHeader(letter: Char) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray.copy(alpha = 0.3f), RectangleShape)
            .padding(start = 10.dp)
    ) {
        Text(text = letter.toString())
    }
}

@Composable
fun ContactItem(data: Users) {
    val icon: ImageVector =
        if (data.userRole == Constants.ResidentRole) Icons.Filled.Person else if (data.userRole == Constants.CommitteeRole) Icons.Filled.SupervisedUserCircle else Icons.Filled.Security

    Card(modifier = Modifier.fillMaxWidth()) {
        Row() {
            Icon(
                imageVector = icon,
                contentDescription = data.userRole,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                Text(text = data.name, style = MaterialTheme.typography.h6)
                Text(text = data.userRole, style = MaterialTheme.typography.subtitle2)
                Divider()
                Text(text = data.contactNumber)
                Text(text = data.email)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun PhonebookPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val user = Users(
                name = "Tester",
                userRole = "Resident",
                contactNumber = "012-3213 4534",
                email = "test@gmail.com"
            )
            ContactItem(user)
        }
    }
}
