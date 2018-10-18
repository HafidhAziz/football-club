package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presenter.base.View
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
interface FavoriteMatchView : View<Any> {
    fun onFavoriteMatchDataReceivedFromDB(favMatchDataFromDB: List<MatchFavorite>)
    fun onFavoriteMatchDataReceived(favMatchData: List<Event>)
}