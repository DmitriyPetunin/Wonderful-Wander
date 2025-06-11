package com.example.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.geo.GeoAction
import com.example.base.event.GeoEvent
import com.example.base.event.post.CreatePostEvent
import com.example.base.state.GeoState
import com.example.base.state.Point
import com.example.presentation.usecase.GetActualGeoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GeoViewModel @Inject constructor(
    private val getActualGeoDataUseCase: GetActualGeoDataUseCase,
    //val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    private val _geoState = MutableStateFlow(GeoState())
    val geoState = _geoState.asStateFlow()

    private val _event = MutableSharedFlow<GeoEvent>()
    val event: SharedFlow<GeoEvent> = _event

    fun onAction(action: GeoAction) {
        when (action) {
            is GeoAction.UpdateCurrentCenter -> {
                updateCurrentCenter(action.latitude, action.longitude)
            }
            is GeoAction.UpdateText ->{
                updateText()
            }

            GeoAction.NavigateToCreateWalkPage -> {
                viewModelScope.launch {
                    _event.emit(GeoEvent.InteractionOne)
                }
            }
        }
    }

    private fun updateCurrentCenter(latitude: Double, longitude: Double) {
        _geoState.update {
            it.copy(point = Point(latitude = latitude, longitude = longitude))
        }
    }

    private fun updateText() {

        Log.d("updateCurrentCenter","latitude = ${geoState.value.point.latitude} longitude = ${geoState.value.point.longitude}")

        viewModelScope.launch {

//            firebaseAnalytics.logEvent("api_call"){
//                param("endpoint", "v1/")
//                param("geocode", string)
//            }
            val response = getActualGeoDataUseCase.invoke(geocodeString = "${geoState.value.point.longitude},${geoState.value.point.latitude}")

            _geoState.update { state ->
                response.fold(
                    onSuccess = { model ->
                        state.copy(
                            text = model.text
                        )
                    },
                    onFailure = {exception ->
                        exception.printStackTrace()
                        state.copy(text = "пу пу пу ошибочка")
                    }
                )
            }
        }
    }
}