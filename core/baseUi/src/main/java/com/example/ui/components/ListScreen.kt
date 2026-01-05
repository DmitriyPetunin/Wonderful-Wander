package com.example.ui.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

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

    LazyColumn(state = lazyListState, modifier = modifier) {
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

