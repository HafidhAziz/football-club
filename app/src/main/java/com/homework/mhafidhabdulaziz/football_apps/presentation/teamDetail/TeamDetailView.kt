package com.homework.mhafidhabdulaziz.football_apps.presentation.teamDetail

import com.homework.mhafidhabdulaziz.football_apps.local.db.TeamFavorite

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
interface TeamDetailView {
    fun onTeamFavoriteDataReceived(favoriteData: List<TeamFavorite>)
}