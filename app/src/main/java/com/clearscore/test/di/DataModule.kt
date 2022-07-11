package com.clearscore.test.di

import com.clearscore.test.BuildConfig
import com.clearscore.test.config.Endpoints.BASE_URL
import com.clearscore.test.data.api.GetScoreApi
import com.clearscore.test.data.dataSource.FetchScoreDataSource
import com.clearscore.test.data.dataSource.FetchScoreDataSourceImpl
import com.clearscore.test.data.repository.ScoreDataRepository
import com.clearscore.test.data.repository.ScoreDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GetScoreApi =
        retrofit.create(GetScoreApi::class.java)

    @Provides
    fun provideDataSource(getScoreApi: GetScoreApi): FetchScoreDataSource =
        FetchScoreDataSourceImpl(getScoreApi)

    @Provides
    fun provideRepository(dataSource: FetchScoreDataSource): ScoreDataRepository =
        ScoreDataRepositoryImpl(dataSource)

}