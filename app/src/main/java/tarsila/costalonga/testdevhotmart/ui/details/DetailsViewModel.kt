package tarsila.costalonga.testdevhotmart.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.model.DetailLocation
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.EMPTY_BODY_REQUEST
import tarsila.costalonga.testdevhotmart.utils.NOT_FOUND_REQUEST
import tarsila.costalonga.testdevhotmart.utils.Resource
import tarsila.costalonga.testdevhotmart.utils.Status

class DetailsViewModel @ViewModelInject constructor(private val locationDetailsAPI: LocationAPI) :
    ViewModel() {

    private val _detailLocation = MutableLiveData<DetailLocation>()
    val detailLocation: LiveData<DetailLocation> = _detailLocation

/*
    var detailResponse : DetailLocation? = null
*/


    private val _statusRequestDetail = MutableLiveData<Status>()
    val statusRequestDetail: LiveData<Status>
        get() = _statusRequestDetail

    var msgDetail: String? = null




    private suspend fun makeRequestDetailLocationAPI(id: Int): Resource<DetailLocation> {

        val retornoDetailLocation = locationDetailsAPI.getDetailsLocation(id)

        return if (retornoDetailLocation.isSuccessful) {
            retornoDetailLocation.body()?.let {
                _detailLocation.value = retornoDetailLocation.body()
              //  detailResponse = retornoDetailLocation.body()
                return@let Resource.success(retornoDetailLocation.body())
            } ?: Resource.error(EMPTY_BODY_REQUEST, null)
        } else {
            Resource.error(NOT_FOUND_REQUEST, null)
        }

    }


    fun requestDetails(id: Int) {
        viewModelScope.launch {
            val makeRequestDetailLocationAPI = makeRequestDetailLocationAPI(id)
            _statusRequestDetail.value = makeRequestDetailLocationAPI.status
            msgDetail = makeRequestDetailLocationAPI.message
        }
    }

}