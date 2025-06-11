package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.event.GeoUiEvent
import com.example.base.state.GeoState
import com.example.presentation.usecase.GetActualGeoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GeoViewModel @Inject constructor(
    private val getActualGeoDataUseCase: GetActualGeoDataUseCase,
    //val firebaseAnalytics: FirebaseAnalytics
):ViewModel() {

    private val _geoState = MutableStateFlow(GeoState())
    val geoState = _geoState.asStateFlow()

    fun onEvent(event: GeoUiEvent){
        when(event){
            is GeoUiEvent.InteractionOne -> {}
            is GeoUiEvent.InteractionTwo -> {
                getText(event.input)
            }
        }
    }

    private fun getText(string: String){
        viewModelScope.launch {

//            firebaseAnalytics.logEvent("api_call"){
//                param("endpoint", "v1/")
//                param("geocode", string)
//            }
            val response = getActualGeoDataUseCase.invoke(string)

            _geoState.value = _geoState.value.copy(response.text)
        }
    }
}