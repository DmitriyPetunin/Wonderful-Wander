package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.GeoAction
import com.example.base.state.GeoState
import com.example.base.state.Point
import com.example.presentation.usecase.GetActualGeoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun onAction(action: GeoAction) {
        when (action) {
            is GeoAction.UpdateCurrentCenter -> {
                updateCurrentCenter(action.latitude, action.longitude)
            }
        }
    }

    private fun updateCurrentCenter(latitude: Double, longitude: Double) {

        _geoState.update {
            it.copy(point = Point(latitude = latitude, longitude = longitude))
        }

        viewModelScope.launch {

//            firebaseAnalytics.logEvent("api_call"){
//                param("endpoint", "v1/")
//                param("geocode", string)
//            }
            val response = runCatching {
                getActualGeoDataUseCase.invoke(geocodeString = "${geoState.value.point.longitude},${geoState.value.point.latitude}")
            }

            _geoState.update {
                it.copy(
                    text = response.fold(
                    onSuccess = {
                        it.text
                    },
                    onFailure = {
                        "ошибка при получении информации"
                    }
                ))
            }
        }
    }
}