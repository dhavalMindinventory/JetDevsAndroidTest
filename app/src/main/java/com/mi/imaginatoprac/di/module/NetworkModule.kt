package com.mi.imaginatoprac.di.module

import android.content.Context
import com.basestructure.app.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val HTTP_REQUEST_TIMEOUT = 2L
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }.asConverterFactory(contentType)
            )
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
            .callTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)

        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideInterceptors(@ApplicationContext context : Context): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        interceptors.add(loggingInterceptor)
        interceptors.add(ChuckInterceptor(context))
        return interceptors
    }
}
