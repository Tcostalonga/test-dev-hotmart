package tarsila.costalonga.testdevhotmart.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.gson.JsonParseException
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.model.DetailLocation
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.*
import java.net.UnknownHostException

class DetailsViewModel @ViewModelInject constructor(private val locationDetailsAPI: LocationAPI) :
    ViewModel() {

    private val _detailLocation = MutableLiveData<DetailLocation>()
    val detailLocation: LiveData<DetailLocation> = _detailLocation


    private val _statusRequestDetail = MutableLiveData<Status>()
    val statusRequestDetail: LiveData<Status>
        get() = _statusRequestDetail

    var msgDetail: String? = null


    private suspend fun makeRequestDetailLocationAPI(id: Int): Resource<DetailLocation> {

        return try {

            val retornoDetailLocation = locationDetailsAPI.getDetailsLocation(id)

            if (retornoDetailLocation.isSuccessful) {
                retornoDetailLocation.body()?.let {
                    _detailLocation.value = retornoDetailLocation.body()
                    //  detailResponse = retornoDetailLocation.body()
                    return@let Resource.success(retornoDetailLocation.body())
                } ?: Resource.error(EMPTY_INVALID_REQUEST, null)
            } else {
                Resource.error(NOT_FOUND_REQUEST, null)
            }
        } catch (e: UnknownHostException) {
            Resource.error(NOT_CONNECTED_REQUEST, null)
        } catch (e: JsonParseException) {
            Resource.error(EMPTY_INVALID_REQUEST, null)
        } catch (e: Exception) {
            Resource.error(NOT_FOUND_REQUEST, null)
        }
    }

    fun requestDetails(id: Int) {
        viewModelScope.launch {
            val makeRequestDetailLocationAPI = makeRequestDetailLocationAPI(id)
            msgDetail = makeRequestDetailLocationAPI.message
            _statusRequestDetail.value = makeRequestDetailLocationAPI.status
        }
    }

}