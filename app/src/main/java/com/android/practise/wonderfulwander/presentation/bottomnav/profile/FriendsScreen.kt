package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.base.model.user.friends.Friend
import com.example.base.state.ListScreenState
import com.example.presentation.viewmodel.FriendsViewModel


@Composable
fun FriendsScreenRoute(
    friendsViewModel: FriendsViewModel
) {
    val state by friendsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        friendsViewModel.loadData()
    }

    FriendsScreen(state = state,)
}



@Composable
fun FriendsScreen(
    state: ListScreenState<Friend>
) {

    Text(text = "Друзья")


    ListScreen(
        state = state,
        onRetry = {},
        onItemClick = {}
    )

}