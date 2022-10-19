package com.ramprasad.rcountries.di

import com.ramprasad.rcountries.network.RetrofitClient
import com.ramprasad.rcountries.repository.AllCountriesRepository
import com.ramprasad.rcountries.repository.AllCountriesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Ramprasad on 7/30/22.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppNetworkModule {
/*
    // API URL
    val BASE_URL =
        "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"
*/

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesNetworkService(okHttpClient: OkHttpClient): RetrofitClient =
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitClient::class.java)

    @Provides
    @Singleton
    fun providesCountryRepository(retrofitClient: RetrofitClient): AllCountriesRepository =
        AllCountriesRepositoryImpl(retrofitClient)

    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
