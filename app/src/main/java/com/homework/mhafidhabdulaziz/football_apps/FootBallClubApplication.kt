package com.homework.mhafidhabdulaziz.football_apps

import android.app.Application
import com.homework.mhafidhabdulaziz.football_apps.common.Constants
import com.homework.mhafidhabdulaziz.football_apps.dependency.AppModule
import com.homework.mhafidhabdulaziz.football_apps.dependency.DaggerNetComponent
import com.homework.mhafidhabdulaziz.football_apps.dependency.NetComponent
import com.homework.mhafidhabdulaziz.football_apps.dependency.NetModule

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class FootBallClubApplication : Application() {

    private var instance: FootBallClubApplication? = null
    fun get(): FootBallClubApplication? {
        return instance
    }

    private var netComponent: NetComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(Constants.SERVICE_BASE_URL))
                .build()
    }

    fun getNetComponent(): NetComponent? {
        return netComponent
    }
}