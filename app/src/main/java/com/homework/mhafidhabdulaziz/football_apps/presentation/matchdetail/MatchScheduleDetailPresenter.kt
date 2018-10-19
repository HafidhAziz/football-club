package com.homework.mhafidhabdulaziz.football_apps.presentation.matchdetail

import android.content.Context
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class MatchScheduleDetailPresenter(private val view: MatchScheduleDetailView,
                                   private val apiRepository: FootBallApiRepository,
                                   private val gson: Gson,
                                   private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun requestTeamDetailData(id: String, isHomeTeam: Boolean) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.getTeamDetail(id)), TeamsDto::class.java)
            }

            view.onTeamDetailDataReceived(data.await(), isHomeTeam)
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