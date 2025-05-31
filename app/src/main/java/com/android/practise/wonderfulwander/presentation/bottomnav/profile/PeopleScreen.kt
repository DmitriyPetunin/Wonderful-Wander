package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.action.profile.PeoplePageAction
import com.example.base.event.people.PeoplePageEvent
import com.example.base.state.ListScreenState
import com.example.presentation.viewmodel.PeopleViewModel


@Composable
fun PeopleScreenRoute(
    listType: String,
    friendsViewModel: PeopleViewModel = hiltViewModel(),
    navigateToPeoplePerson: (String) -> Unit
) {
    val state by friendsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        friendsViewModel.event.collect { event ->
            when (event) {
                is PeoplePageEvent.NavigateToPersonWithUserId -> {
                    navigateToPeoplePerson(event.id)
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        when (listType) {
            "friends" -> friendsViewModel.loadFriends()
            "following" -> friendsViewModel.loadFollowing()
            "followers" -> friendsViewModel.loadFollowers()
        }
    }

    PeopleScreen(state = state, friendsViewModel::onAction)
}


@Composable
fun PeopleScreen(
    state: ListScreenState,
    onAction: (PeoplePageAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = state.people,
            style = MaterialTheme.typography.displayMedium
        )
        ListScreen(
            state = state,
            onItemClick = { id -> onAction(PeoplePageAction.SubmitPersonItem(userId = id)) }
        )
    }

}