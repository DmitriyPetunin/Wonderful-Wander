package com.example.feature.profile.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.feature.profile.api.action.PeoplePageAction
import com.example.base.enums.PeopleEnum.Companion.fromString
import com.example.feature.profile.api.event.PeoplePageEvent
import com.example.base.model.user.People
import com.example.feature.profile.api.state.ListScreenState
import com.example.feature.profile.impl.viewmodel.PeopleViewModel
import com.example.ui.components.ListScreen


@Composable
fun PeopleScreenRoute(
    listType: String,
    friendsViewModel: PeopleViewModel = hiltViewModel(),
    navigateToPersonProfile: (String) -> Unit
) {
    val state by friendsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        friendsViewModel.event.collect { event ->
            when (event) {
                is PeoplePageEvent.NavigateToPersonProfileWithUserId -> {
                    navigateToPersonProfile(event.userId)
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        friendsViewModel.onAction(PeoplePageAction.UpdatePeopleState(input = fromString(listType)))
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
            text = state.people.toString(),
            style = MaterialTheme.typography.displayMedium
        )
        ListScreen(
            items = state.listOfPeople,
            isLoading = state.isLoading,
            endReached = state.endReached,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),

            loadMore = { onAction(PeoplePageAction.LoadMore) },
            itemContent = { person ->
                PersonListItem(
                    man = person,
                    onItemClick = { id ->
                        onAction(PeoplePageAction.SubmitPersonItem(userId = id))
                    }
                )
            }
        )
    }
}


@Composable
private fun PersonListItem(
    man: People,
    onItemClick: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(onClick = { onItemClick(man.userId) }),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .padding(2.dp)
            ) {
                AsyncImage(
                    model = man.avatarUrl,
                    contentDescription = "User avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = man.username,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
