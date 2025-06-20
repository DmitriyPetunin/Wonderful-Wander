package com.android.practise.wonderfulwander.presentation.walk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base.model.user.People


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchBarCustom(
    query: String,
    items: List<T>,
    active:Boolean,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    searchStringProvider: (T) -> String,
    itemContent: @Composable (T) -> Unit,
    modifier:Modifier = Modifier
) {

    val filteredItems = remember(items, query) {
        items.filter { item ->
            searchStringProvider(item).contains(query, ignoreCase = true)
        }
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
    ) {
        SearchBar(
            query = query,
            onQueryChange = { text -> onQueryChange(text) },
            onSearch = { onActiveChange(false) },
            active = active,
            onActiveChange = { onActiveChange(it) },
            placeholder = { Text("Поиск") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingIcon = {
                if (active)
                    IconButton(
                        onClick = {
                            onActiveChange(false)
                            onQueryChange("")
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Clear search"
                        )
                    }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
            tonalElevation = 0.dp,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 168.dp)
            ) {
                items(count = filteredItems.size) { index ->
                    val item = filteredItems[index]
                    itemContent(item)
                }
            }
        }
    }
}