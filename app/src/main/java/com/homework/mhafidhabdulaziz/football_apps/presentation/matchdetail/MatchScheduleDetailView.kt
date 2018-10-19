package com.homework.mhafidhabdulaziz.football_apps.presentation.matchdetail

import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
interface MatchScheduleDetailView {
    fun onTeamDetailDataReceived(teamData: TeamsDto, isHomeTeam: Boolean)
    fun onMatchFavoriteDataReceived(favoriteData: List<MatchFavorite>)
}