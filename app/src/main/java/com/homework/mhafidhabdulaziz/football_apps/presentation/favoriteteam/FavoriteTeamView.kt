package com.homework.mhafidhabdulaziz.football_apps.presentation.favoriteteam

import com.homework.mhafidhabdulaziz.football_apps.local.db.TeamFavorite
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team

/**
 * Created by M Hafidh Abdul Aziz on 10/27/2018.
 */
interface FavoriteTeamView {
    fun showLoading()
    fun hideLoading()
    fun onFavoriteTeamDataReceivedFromDB(favTeamDataFromDB: List<TeamFavorite>)
    fun onFavoriteTeamDataReceived(favTeamData: List<Team>)
}