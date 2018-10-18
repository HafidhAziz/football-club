package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch

import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

interface LastMatchView {
    fun showLoading()
    fun hideLoading()
    fun onLastMatchDataReceived(matchData: List<Event>)
}
