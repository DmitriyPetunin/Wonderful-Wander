package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.interceptor.TokenInterceptor
import com.example.network.service.GeoService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideGeoService(
        retrofit: Retrofit
    ): GeoService {
        return retrofit.create(GeoService::class.java)
    }


    @Provides
    @Singleton
    fun provideHttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                println("API call made to ${request.url}")
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()

    }


    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {

        val converter = "application/json".toMediaType()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(converter))
            .build()
    }

}