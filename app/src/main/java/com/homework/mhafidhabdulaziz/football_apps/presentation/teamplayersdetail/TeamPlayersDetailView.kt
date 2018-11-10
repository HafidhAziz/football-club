package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayersdetail

import com.homework.mhafidhabdulaziz.football_apps.service.entity.Player

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
interface TeamPlayersDetailView {
    fun showLoading()
    fun hideLoading()
    fun onReceivedPlayerData(playerData: Player)
}