package tarsila.costalonga.testdevhotmart.network

import retrofit2.Response
import retrofit2.http.GET
import tarsila.costalonga.testdevhotmart.model.ListLocations

interface LocationAPI {

    @GET("/locations")
   suspend fun getAllLocations(): Response<ListLocations>
}

const val BASE_URL_LOCATION_API = "https://hotmart-mobile-app.herokuapp.com"