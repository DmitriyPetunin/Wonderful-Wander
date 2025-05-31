package com.android.practise.wonderfulwander.presentation.walk

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.base.action.walk.CreateWalkAction
import com.example.base.model.user.People
import com.example.base.state.CreateWalkState
import com.example.presentation.viewmodel.CreateWalkViewModel

@Composable
fun CreateWalkPageRoute(
    createWalkViewModel: CreateWalkViewModel = hiltViewModel()

) {

    LaunchedEffect(Unit) {
        createWalkViewModel.onAction(CreateWalkAction.GetAllFriends)
    }

    val state by createWalkViewModel.state.collectAsState()

    CreateWalkPage(state = state, createWalkViewModel::onAction)

}

@Composable
fun CreateWalkPage(
    state: CreateWalkState,
    onAction: (CreateWalkAction) -> Unit
) {

    val listOfPeople: List<People> = state.listOfPeople

    val listOfResult = remember { mutableStateListOf<People>()}

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBarCustom(
                query = state.queryParam,
                items = listOfPeople,
                onQueryChange = { onAction(CreateWalkAction.UpdateQueryParam(it)) },
                onResultClick = { friend ->
                    listOfResult.addAll(listOfPeople.filter { it.equals(friend) })
                    Log.d("TEST-TAG", "${listOfResult.size}")
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(listOfResult) { friend ->

                //ListItem

                Row() {
                    AsyncImage(
                        model = friend.avatarUrl,
                        contentDescription = "",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                    Text(friend.username)
                }
            }
        }
    }
}