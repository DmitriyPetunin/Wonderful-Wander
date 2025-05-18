package com.android.practise.wonderfulwander.presentation.bottomnav

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.event.GeoUiAction
import com.example.base.state.GeoState
import com.example.presentation.viewmodel.GeoViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
@Composable
fun MapScreenRoute(
    geoViewModel: GeoViewModel = hiltViewModel()
){
    val mapScreenState by geoViewModel.geoState.collectAsState()

    MapScreen(state = mapScreenState,geoViewModel::onAction)
}

@Composable
fun MapScreen(
    state: GeoState,
    onAction: (GeoUiAction) -> Unit
) {
    val mapView = remember { MutableStateFlow<MapView?>(null) }

    val context = LocalContext.current

    var currentCenter by remember { mutableStateOf(Point(55.78874, 49.12214)) } // Тукая

    var counterState by remember { mutableStateOf(0) }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    mapView.value = this
                }
            }, modifier = Modifier.fillMaxSize(),

            update = { mapView ->

                mapView.mapWindow.map.move(
                    CameraPosition(
                        currentCenter, ZOOM, AZIMUTH, TILT
                    ), Animation(Animation.Type.SMOOTH, 1.5f), null
                )
                //Log.d("Test-Tag","совершили переход в точку ${location.value.latitude} ${location.value.longitude}")


                mapView.mapWindow.map.addCameraListener(object : CameraListener {
                    override fun onCameraPositionChanged(
                        map: Map,
                        cameraPosition: CameraPosition,
                        cameraUpdateReason: CameraUpdateReason,
                        isFinished: Boolean
                    ) {
                        if (isFinished) {
                            currentCenter = cameraPosition.target
                            Log.d(
                                "TEST-TAG",
                                " ${currentCenter.latitude}, ${currentCenter.longitude}"
                            )
                        }
                    }
                })
            }
        )
        Text(
            text = state.text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.End,
        ) {
            Button(
                onClick = { mapView.value?.let { changeZoomByStep(mapView = it, value = ZOOM_STEP) } },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Button(
                onClick = { mapView.value?.let { changeZoomByStep(mapView = it, value = -ZOOM_STEP) } },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            counterState += 1
        }
    }

    LaunchedEffect(counterState) {

        val newCenter = currentCenter

        Log.d("TEST-TAG", "newCenter = ${newCenter.latitude} + ${newCenter.longitude} ")

        onAction(GeoUiAction.InteractionTwo(input = "${newCenter.longitude},${newCenter.latitude}"))

    }
}

fun changeZoomByStep(mapView: MapView, value: Float) {
    with(mapView.mapWindow.map.cameraPosition) {
        mapView.mapWindow.map.move(
            CameraPosition(target, zoom + value, azimuth, tilt),
            Animation(com.yandex.mapkit.Animation.Type.SMOOTH, 0.5f),
            null,
        )
    }
}

private const val ZOOM_STEP = 1f

private const val ZOOM = 17.0f

private const val AZIMUTH = 150.0f

private const val TILT = 00.0f
