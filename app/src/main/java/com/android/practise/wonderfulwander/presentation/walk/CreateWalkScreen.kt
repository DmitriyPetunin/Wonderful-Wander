package com.android.practise.wonderfulwander.presentation.walk

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.base.action.walk.CreateWalkAction
import com.example.base.model.user.People
import com.example.base.state.CreateWalkState
import com.example.presentation.viewmodel.CreateWalkViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CreateWalkScreenRoute(
    createWalkViewModel: CreateWalkViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        createWalkViewModel.onAction(CreateWalkAction.GetAllFriends)
    }

    val state by createWalkViewModel.state.collectAsState()

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    } else {
        CreateWalkScreen(state = state, createWalkViewModel::onAction)
    }
}

@Composable
fun CreateWalkScreen(
    state: CreateWalkState,
    onAction: (CreateWalkAction) -> Unit
) {

    val listOfFriends = remember { mutableStateListOf<People>().apply { addAll(state.listOfFriends) } }

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
                items = listOfFriends,
                active = true,
                onActiveChange = {},//TODO
                onQueryChange = { onAction(CreateWalkAction.UpdateQueryParam(it)) },
                searchStringProvider = {it.username},
                itemContent = { person ->
                    FriendListItemCustom(
                        people = person,
                        onResultClick = {
                            onAction(CreateWalkAction.AddFriend(person))
                        }
                    )
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(listOfResult) { friend ->
                FriendListItem(
                    friend = friend,
                )
            }
        }
        StartPointSection(state = state)
    }
}

@Composable
fun FriendListItem(
    friend: People,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = friend.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = friend.username,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Готов к прогулке",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Walking",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}



@Composable
private fun FriendListItemCustom(
    people: People,
    onResultClick: () -> Unit
) {

    ListItem(
        headlineContent = { Text(people.username) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .clickable {
                onResultClick()
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    )

}
@Composable
private fun StartPointSection(
    state: CreateWalkState
){

    val mapView = remember { MutableStateFlow<MapView?>(null) }


    val currentCenter = state.point

    var counterState by remember { mutableStateOf(0) }


    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Место сбора:",
            style = MaterialTheme.typography.headlineMedium,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .clip(CircleShape.copy(CornerSize(24.dp)))
        ){
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        mapView.value = this
                    }
                }, modifier = Modifier.fillMaxSize(),

                update = { mapView ->
                    if(counterState == 0){
                        mapView.mapWindow.map.move(
                            CameraPosition(
                                Point(currentCenter.latitude, currentCenter.longitude), ZOOM, AZIMUTH, TILT
                            ), Animation(Animation.Type.SMOOTH, 1.5f), null
                        )
                    }
                }
            )
        }
    }
}

private const val ZOOM_STEP = 1f

private const val ZOOM = 17.0f

private const val AZIMUTH = 0.0f

private const val TILT = 00.0f