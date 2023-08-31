package com.gaosi.moduledemo.videModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaosi.moduledemo.repository.LocationRepository
import com.gaosi.moduledemo.utils.Constants.TAG
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/17 10:45
 *
 **/
class LocationViewModel(
) : ViewModel(){

    private val locationRepository: LocationRepository = LocationRepository(viewModelScope)

    private val _locationState = MutableStateFlow("")
    val locationState : StateFlow<String> = _locationState

    init {
        Log.d(TAG,"LocationViewModel init")
        viewModelScope.launch {
            locationRepository.locations.collect {
                _locationState.value = it
            }
        }
    }
}