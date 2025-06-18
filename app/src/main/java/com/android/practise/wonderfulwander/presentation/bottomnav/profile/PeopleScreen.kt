package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.action.profile.PeoplePageAction
import com.example.base.event.people.PeoplePageEvent
import com.example.base.state.ListScreenState
import com.example.base.state.PeopleEnum.Companion.fromString
import com.example.presentation.viewmodel.PeopleViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


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
                ListItem(
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
fun <T> ListScreen(
    items: List<T>,
    isLoading: Boolean,
    endReached: Boolean,
    loadMore: () -> Unit,
    itemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier
) {

    val lazyListState = rememberLazyListState()


    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo }
            .map { layoutInfo ->
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                val reachedEnd = lastVisibleItem?.index == layoutInfo.totalItemsCount - 1

                Log.d("TEST-TAG", "Checking scroll position: " +
                        "lastVisibleIndex=${lastVisibleItem?.index}, " +
                        "totalItems=${layoutInfo.totalItemsCount}, " +
                        "reachedEnd=$reachedEnd  " +

                        "isLoading = ${isLoading}  " +
                        "endReached = ${endReached}\""
                )

                reachedEnd
            }
            .distinctUntilChanged()
            .collect { reachedEnd ->
                if (reachedEnd) {
                    Log.d("TEST-TAG", "END OF LIST REACHED! Triggering load more...")
                    loadMore()
                }
            }
    }

    LazyColumn(state  = lazyListState) {
        items(items) { item ->
            itemContent(item)
        }
        item {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = modifier)
                }
                endReached && items.isNotEmpty() -> {
                    Text(
                        text = "Конец списка",
                        modifier = modifier
                    )
                }
            }
        }
    }
}