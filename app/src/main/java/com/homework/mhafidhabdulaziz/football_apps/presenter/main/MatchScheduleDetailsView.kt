package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presenter.base.View

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
interface MatchScheduleDetailsView : View<Any> {
    fun onTeamDetailDataReceived(response: String, isHomeTeam: Boolean)
    fun onMatchFavoriteDataReceived(favoriteData: List<MatchFavorite>)
}