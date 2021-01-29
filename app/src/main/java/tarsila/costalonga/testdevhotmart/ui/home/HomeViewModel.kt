package tarsila.costalonga.testdevhotmart.ui.home

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.model.Images
import tarsila.costalonga.testdevhotmart.model.ListLocations
import tarsila.costalonga.testdevhotmart.network.ImagesAPI
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.*
import java.net.UnknownHostException


class HomeViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    private val locationAPI: LocationAPI,
    private val imagesAPI: ImagesAPI
) :
    ViewModel() {

    private val _locations = MutableLiveData<ListLocations>()
    val locations: LiveData<ListLocations> = _locations

    private val _statusRequest = MutableLiveData<Status>()
    val statusRequest: LiveData<Status>
        get() = _statusRequest

    var _images = MutableLiveData<Images>()
    val images: LiveData<Images> = _images


    var msgHome: String? = null

    init {
        requestLocations()
    }

    private suspend fun makeRequestLocationsAPI(): Resource<ListLocations> {
        return try {
            val retornoLocations = locationAPI.getAllLocations()

            if (retornoLocations.isSuccessful) {
                retornoLocations.body()?.let {
                    _locations.value = retornoLocations.body()
                    return@let Resource.success(retornoLocations.body())
                } ?: Resource.error(context.getString(R.string.EMPTY_INVALID_REQUEST), null)
            } else {
                Resource.error(context.getString(R.string.NOT_FOUND_REQUEST), null)
            }
        } catch (e: UnknownHostException) {
            Resource.error(context.getString(R.string.NOT_CONNECTED_REQUEST), null)
        } catch (e: JsonParseException) {
            Resource.error(context.getString(R.string.EMPTY_INVALID_REQUEST), null)
        } catch (e: Exception) {
            Resource.error(context.getString(R.string.NOT_FOUND_REQUEST), null)
        }
    }

    fun requestLocations() {
        viewModelScope.launch {
            val makeRequestLocationsApi = makeRequestLocationsAPI()
            msgHome = makeRequestLocationsApi.message
            _statusRequest.value = makeRequestLocationsApi.status
        }
    }


    private suspend fun makeRequestImagesAPI(qntImg: Int) {

        try {
            val retornoImgs = imagesAPI.getImages(qntImg)

            retornoImgs.body()?.let {
                _images.value = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun requestImages(qntImg: Int) {
        viewModelScope.launch {
            val makeRequestImagesAPI = makeRequestImagesAPI(qntImg)


        }
    }


}