package tarsila.costalonga.testdevhotmart.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.model.Images
import tarsila.costalonga.testdevhotmart.model.ListLocations
import tarsila.costalonga.testdevhotmart.network.ImagesAPI
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.*

class HomeViewModel @ViewModelInject constructor(
    private val locationAPI: LocationAPI,
    private val imagesAPI: ImagesAPI
) :
    ViewModel() {

    private val _locations = MutableLiveData<ListLocations>()
    val locations: LiveData<ListLocations> = _locations

    private val _statusRequest = MutableLiveData<Status>()
    val statusRequest: LiveData<Status>
        get() = _statusRequest

    var images = Images()


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

    fun requestLocations() {
        viewModelScope.launch {
            val makeRequestLocationsApi = makeRequestLocationsAPI()
            msg = makeRequestLocationsApi.message
            _statusRequest.value = makeRequestLocationsApi.status
        }
    }


    private suspend fun makeRequestImagesAPI(qntImg: Int) {

        val retornoImgs = imagesAPI.getImages(qntImg)

        retornoImgs.body()?.let {
            images = it
        }
    }


    fun requestImages(qntImg: Int) {
        viewModelScope.launch {
            val makeRequestImagesAPI = makeRequestImagesAPI(qntImg)

        }
    }


}