package com.android.practise.wonderfulwander.presentation.walk

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
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
fun SearchBarCustom(
    query: String,
    items: List<People>,
    onQueryChange: (String) -> Unit,
    onResultClick: (People) -> Unit
) {

    var active by remember { mutableStateOf(false) }

    val listOfFriends = remember { items.toMutableStateList() }

    val filteredItems = listOfFriends.filter { friend ->
        friend.username.contains(query, ignoreCase = true)
    }

    SearchBar(
        query = query,
        onQueryChange = { text -> onQueryChange(text) },
        onSearch = { active = false },
        active = active,
        onActiveChange = { active = it },
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
                        active = !active
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
                .height(168.dp)
        ) {
            items(count = filteredItems.size) { index ->
                val friend = filteredItems[index]

                FriendListItemCustom(
                    people = friend,
                    onResultClick = { item ->
                        listOfFriends.remove(item)
                        onResultClick(item)
                    }
                )
            }
        }
    }
}