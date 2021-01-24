package tarsila.costalonga.testdevhotmart.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.model.DetailLocation
import tarsila.costalonga.testdevhotmart.model.ListLocations
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.*

class HomeViewModel @ViewModelInject constructor(private val locationAPI: LocationAPI) :
    ViewModel() {

    private val _locations = MutableLiveData<ListLocations>()
    val locations: LiveData<ListLocations> = _locations

    private val _detailocation = MutableLiveData<DetailLocation>()
    val detailocation: LiveData<DetailLocation> = _detailocation

    private val _statusRequest = MutableLiveData<Status>()
    val statusRequest: LiveData<Status>
        get() = _statusRequest

    var msg: String? = null

    private suspend fun makeRequestLocationsAPI(): Resource<ListLocations> {
        return try {
            val retornoLocations = locationAPI.getAllLocations()

            if (retornoLocations.isSuccessful) {
                retornoLocations.body()?.let {
                    _locations.value = retornoLocations.body()
                    return@let Resource.success(retornoLocations.body())
                } ?: Resource.error(EMPTY_BODY_REQUEST, null)
            } else {
                Resource.error(NOT_FOUND_REQUEST, null)
            }
        } catch (e: Exception) {
            Resource.error(NOT_CONNECTED_REQUEST, null)

        }
    }

    fun requestLocationsAPI() {
        viewModelScope.launch {
            val makeRequestLocationsApi = makeRequestLocationsAPI()
            msg = makeRequestLocationsApi.message
            _statusRequest.value = makeRequestLocationsApi.status

        }
    }


    suspend fun makeRequestDetailLocationAPI(id: Int): Resource<DetailLocation> {
        return try {
            val retornoDetailLocation = locationAPI.getDetailsLocation(id)

            if (retornoDetailLocation.isSuccessful) {
                retornoDetailLocation.body()?.let {
                    _detailocation.value = retornoDetailLocation.body()
                    return@let Resource.success(retornoDetailLocation.body())
                } ?: Resource.error(EMPTY_BODY_REQUEST, null)
            } else {
                Resource.error(NOT_FOUND_REQUEST, null)
            }
        } catch (e: Exception) {
            Resource.error(NOT_CONNECTED_REQUEST, null)
        }
    }


    fun requestDetailsResponse(id: Int) {
        viewModelScope.launch {
            makeRequestDetailLocationAPI(id)

        }
    }


}