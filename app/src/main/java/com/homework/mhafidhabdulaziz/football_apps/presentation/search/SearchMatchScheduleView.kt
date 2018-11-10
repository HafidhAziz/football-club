package com.homework.mhafidhabdulaziz.football_apps.presentation.search

import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
interface SearchMatchScheduleView {
    fun showLoading()
    fun hideLoading()
    fun onSearchMatchDataReceived(matchData: List<Event>)
}