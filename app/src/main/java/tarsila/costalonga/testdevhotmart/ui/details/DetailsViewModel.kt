package tarsila.costalonga.testdevhotmart.ui.details

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.gson.JsonParseException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.model.DetailLocation
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.*
import java.net.UnknownHostException

class DetailsViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    private val locationDetailsAPI: LocationAPI) :
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
                } ?: Resource.error(context.getString(R.string.EMPTY_INVALID_REQUEST), null)
            } else {
                Resource.error(context.getString(R.string.NOT_FOUND_REQUEST), null)
            }
        } catch (e: UnknownHostException) {
            Resource.error(context.getString(R.string.NOT_CONNECTED_REQUEST), null)
        } catch (e: JsonParseException) {
            Resource.error(context.getString(R.string.EMPTY_INVALID_REQUEST), null)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.getString(R.string.NOT_FOUND_REQUEST), null)
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