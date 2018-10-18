package com.homework.mhafidhabdulaziz.football_apps.presentation.matchdetail

import android.content.Context
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class MatchScheduleDetailsPresenter(private val view: MatchScheduleDetailsView,
                                    private val apiRepository: FootBallApiRepository,
                                    private val gson: Gson) {

    fun requestTeamDetailData(id: String, isHomeTeam: Boolean) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(FootBallClubRestApi.getTeamDetail(id)),
                    TeamsDto::class.java
            )

            uiThread {
                view.onTeamDetailDataReceived(data, isHomeTeam)
            }
        }
    }

    fun insertMatchDataToFavorite(context: Context, eventId: String, homeTeamId: String, awayTeamId: String) {
        context.database.use {
            insert("TABLE_FAVORITE", "EVENT_ID" to eventId, "HOME_TEAM" to homeTeamId, "AWAY_TEAM" to awayTeamId)
        }

    }

    fun deleteMatchDataToFavorite(context: Context, eventId: String) {
        context.database.use {
            delete("TABLE_FAVORITE", "(EVENT_ID = {id})", "id" to eventId)
        }
    }

    fun selectMatchDataFromFavorite(context: Context, eventId: String) {
        return context.database.use {
            val result = select("TABLE_FAVORITE").whereArgs("(EVENT_ID = {id})", "id" to eventId)
            view.onMatchFavoriteDataReceived(result.parseList(classParser()))
        }
    }
}