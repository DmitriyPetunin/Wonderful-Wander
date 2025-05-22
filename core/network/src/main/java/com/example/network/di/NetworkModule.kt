package com.example.network.di

import android.content.Context
import com.example.base.SessionManager
import com.example.network.BuildConfig
import com.example.network.interceptor.AuthInterceptor
import com.example.network.interceptor.TokenInterceptor
import com.example.network.service.auth.AuthService
import com.example.network.service.geo.GeoService
import com.example.network.service.user.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideGeoService(
        @Named(GeoCoderRetrofit)
        retrofit: Retrofit
    ): GeoService {
        return retrofit.create(GeoService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(
        @Named(ApiRetrofit)
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(
        @Named("AuthRetrofit")
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }


    @Provides
    @Singleton
    @Named(GeoCoderClient)
    fun provideGeoCoderClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return createOkHttpClient(tokenInterceptor)
    }

    @Provides
    @Singleton
    @Named(ApiClient)
    fun provideApiClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return createOkHttpClient(authInterceptor)
    }

    @Provides
    @Singleton
    fun provideSessionManager(
        @ApplicationContext context: Context
    ):SessionManager = SessionManager(context = context)


    @Provides
    @Singleton
    @Named("AuthOkHttpClient")  // Клиент без AuthInterceptor (для AuthService)
    fun provideAuthOkHttpClient(): OkHttpClient {
        return createOkHttpClient()  // Без AuthInterceptor!
    }


    @Provides
    @Singleton
    @Named("AuthRetrofit")  // Retrofit для AuthService (без цикла)
    fun provideAuthRetrofit(
        @Named("AuthOkHttpClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }




    @Provides
    @Singleton
    fun provideAuthInterceptor(
        sessionManager: SessionManager,
        authService: Lazy<AuthService>
    ) = AuthInterceptor(authService = authService, sessionManager = sessionManager)








    private fun createOkHttpClient(vararg interceptors: Interceptor):OkHttpClient{
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
            .apply {
                interceptors.forEach { addInterceptor(it) }
            }
            .build()
    }


    @Provides
    @Named(value = GeoCoderRetrofit)
    fun provideGeoCoderRetrofit(
        @Named(value = GeoCoderClient)
        client: OkHttpClient
    ): Retrofit {

        val converter = "application/json".toMediaType()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.GEO_CODER_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(converter))
            .build()
    }

    @Provides
    @Named(value = ApiRetrofit)
    fun provideApiRetrofit(
        @Named(ApiClient)
        client: OkHttpClient
    ): Retrofit {

        val converter = "application/json".toMediaType()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(converter))
            .build()
    }

    companion object {
        const val GeoCoderRetrofit = "GeoCoderRetrofit"
        const val ApiRetrofit = "ApiRetrofit"

        const val GeoCoderClient = "GeoCoderClient"
        const val ApiClient = "ApiClient"
    }

}