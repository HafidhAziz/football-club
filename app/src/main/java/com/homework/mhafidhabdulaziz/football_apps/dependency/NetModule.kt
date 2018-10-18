package com.homework.mhafidhabdulaziz.football_apps.dependency

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

@Module
class NetModule(internal var mBaseUrl: String) {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(mBaseUrl)
                .build()
    }
}
