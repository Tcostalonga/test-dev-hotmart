package tarsila.costalonga.testdevhotmart.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tarsila.costalonga.testdevhotmart.model.Images

interface ImagesAPI {

     @GET("api/?key=20017374-57724e752ff7e58de3e00d314&image_type=photo&category=buildings")
    suspend fun getImages(@Query("per_page")numItens: Int): Response<Images>

}