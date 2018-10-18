package com.homework.mhafidhabdulaziz.football_apps.presentation.teams

import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 * NOTE : The class is build as following Dicoding module and exercise
 * So, it may be familiar
 */
interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun onReceivedTeamList(data: List<Team>)
}