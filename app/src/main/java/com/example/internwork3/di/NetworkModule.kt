package com.example.internwork3.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.internwork3.data.source.remote.ProductService
import com.example.internwork3.util.Constans.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context) = ChuckerInterceptor.Builder(context).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor) = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val originRequest = chain.request()
            val modifiedRequest = originRequest.newBuilder()
                .addHeader("store", "canerture")
                .build()
            chain.proceed(modifiedRequest)
        }
        addInterceptor(chuckerInterceptor)
        //change it with above comment (removed)

        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()



    @Provides
    @Singleton

    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) = retrofit.create<ProductService>()

}

