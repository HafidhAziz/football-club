package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import com.homework.mhafidhabdulaziz.football_apps.presenter.base.View

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

interface NextMatchView : View<Any> {
    fun onNextMatchDataReceived(response: String)
}
