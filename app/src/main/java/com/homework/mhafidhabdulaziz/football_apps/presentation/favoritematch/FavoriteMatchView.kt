package com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch

import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
interface FavoriteMatchView {
    fun showLoading()
    fun hideLoading()
    fun onFavoriteMatchDataReceivedFromDB(favMatchDataFromDB: List<MatchFavorite>)
    fun onFavoriteMatchDataReceived(favMatchData: List<Event>)
}