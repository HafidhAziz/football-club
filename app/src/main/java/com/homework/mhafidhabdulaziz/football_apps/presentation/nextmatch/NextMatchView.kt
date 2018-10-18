package com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch

import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun onNextMatchDataReceived(matchData: List<Event>)
}
