package com.homework.mhafidhabdulaziz.football_apps.dependency

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

@Module
internal class AppModule(var mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }
}
