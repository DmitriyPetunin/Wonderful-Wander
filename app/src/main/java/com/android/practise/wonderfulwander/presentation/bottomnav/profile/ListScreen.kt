package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base.model.user.friends.Friend
import com.example.base.state.ListScreenState


@Composable
fun <T> ListScreen(
    state: ListScreenState<T>,
    onRetry: () -> Unit,
    onItemClick: (T) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(state.listOfPeople) { model ->
                ListItem(model = model, onItemClick = {})
            }
        }
    }
}