package com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch

import android.content.Context
import android.os.Handler
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class FavoriteMatchPresenter(private val view: FavoriteMatchView,
                             private val apiRepository: FootBallApiRepository,
                             private val gson: Gson) {


    fun selectMatchDataFromDB(context: Context) {
        view.showLoading()
        return context.database.use {
            val result = select("TABLE_FAVORITE")
            view.onFavoriteMatchDataReceivedFromDB(result.parseList(classParser()))
        }
    }

    fun getMatchDataFavorite(favMatchData: List<MatchFavorite>) {
        val eventList: MutableList<Event> = mutableListOf()
        for (favMatchEvent in favMatchData) {
            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(FootBallClubRestApi.getMatchEventById(favMatchEvent.idEvent)),
                        MatchScheduleDto::class.java
                )

                uiThread {
                    eventList.add(data.events[0])
                    val handler = Handler()
                    handler.postDelayed({
                        view.hideLoading()
                        view.onFavoriteMatchDataReceived(eventList)
                    }, 1000)
                }
            }
        }

        if (favMatchData.isEmpty()) {
            view.hideLoading()
            view.onFavoriteMatchDataReceived(eventList)
        }
    }
}