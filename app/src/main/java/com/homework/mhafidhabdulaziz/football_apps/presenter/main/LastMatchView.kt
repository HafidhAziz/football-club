package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import com.homework.mhafidhabdulaziz.football_apps.presenter.base.View

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

interface LastMatchView : View<Any> {
    fun onLastMatchDataReceived(response: String)
}
