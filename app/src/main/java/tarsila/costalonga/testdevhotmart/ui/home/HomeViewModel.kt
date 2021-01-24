package tarsila.costalonga.testdevhotmart.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.network.LocationAPI

class HomeViewModel @ViewModelInject constructor(private val locationAPI: LocationAPI): ViewModel() {

fun makeRequestLocationsAPI() {
    viewModelScope.launch {
        val retornoLocationsAPI = locationAPI.getAllLocations()


    }

}

}