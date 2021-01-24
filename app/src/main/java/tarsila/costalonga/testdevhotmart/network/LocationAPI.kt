package tarsila.costalonga.testdevhotmart.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import tarsila.costalonga.testdevhotmart.model.DetailLocation
import tarsila.costalonga.testdevhotmart.model.ListLocations

interface LocationAPI {

   @GET("/locations")
   suspend fun getAllLocations(): Response<ListLocations>

   @GET("/locations/{id}")
   suspend fun getDetailsLocation(@Path("id")id: Int): Response<DetailLocation>

}

