package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.base.model.user.People
import com.example.base.state.ListScreenState


@Composable
fun ListScreen(
    state: ListScreenState,
    onItemClick: (String) -> Unit
) {
    LazyColumn {
        items(state.listOfPeople) { model ->
            ListItem(man = model, onItemClick = { onItemClick(model.userId)})
        }
    }
}