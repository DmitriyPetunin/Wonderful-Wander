package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _text = MutableStateFlow("55.78874, 49.12214")
    val text = _text.asStateFlow()

    fun getText(string: String){
        viewModelScope.launch {

//            firebaseAnalytics.logEvent("api_call"){
//                param("endpoint", "v1/")
//                param("geocode", string)
//            }
            val response = getActualGeoDataUseCase.invoke(string)

            _text.value = response.text
        }
    }
}