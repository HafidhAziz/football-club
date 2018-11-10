package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayers

import com.homework.mhafidhabdulaziz.football_apps.service.entity.Player

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
interface TeamPlayersView {
    fun showLoading()
    fun hideLoading()
    fun onReceivedPlayerList(playerData: List<Player>)
}