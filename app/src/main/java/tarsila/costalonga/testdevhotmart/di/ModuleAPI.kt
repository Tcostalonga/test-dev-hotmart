package tarsila.costalonga.testdevhotmart.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tarsila.costalonga.testdevhotmart.network.BASE_URL_LOCATION_API
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ModuleAPI {

    @Singleton
    @Provides
    fun provideLocationsAPI(): LocationAPI {
        return  Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL_LOCATION_API)
            .build()
            .create(LocationAPI::class.java)
    }

}