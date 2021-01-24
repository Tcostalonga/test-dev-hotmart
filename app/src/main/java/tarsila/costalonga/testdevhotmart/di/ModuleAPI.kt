package tarsila.costalonga.testdevhotmart.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tarsila.costalonga.testdevhotmart.network.LocationAPI
import tarsila.costalonga.testdevhotmart.utils.BASE_URL_LOCATION_API
import java.util.concurrent.TimeUnit
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
            .client(okHttpClient())
            .build()
            .create(LocationAPI::class.java)
    }


    fun okHttpClient(): OkHttpClient {

        val okhttpInterceptor = HttpLoggingInterceptor()

        okhttpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(okhttpInterceptor)

        return okHttpClient.build()
    }

}