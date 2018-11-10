package com.homework.mhafidhabdulaziz.football_apps.presentation.favoriteteam

import android.content.Context
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.local.db.TeamFavorite
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * Created by M Hafidh Abdul Aziz on 10/27/2018.
 */
class FavoriteTeamPresenter(private val view: FavoriteTeamView,
                            private val apiRepository: FootBallApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun selectTeamDataFromDB(context: Context) {
        view.showLoading()
        return context.database.use {
            val result = select("TABLE_TEAM_FAV")
            view.onFavoriteTeamDataReceivedFromDB(result.parseList(classParser()))
        }
    }

    fun getTeamDataFavorite(favTeamData: List<TeamFavorite>) {
        val teamList: MutableList<Team> = mutableListOf()
        for (favTeam in favTeamData) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(FootBallClubRestApi.getTeamDetail(favTeam.idTeam)),
                            TeamsDto::class.java)
                }

                teamList.add(data.await().teams[0])
                view.hideLoading()
                view.onFavoriteTeamDataReceived(teamList)
            }
        }
        if (favTeamData.isEmpty()) {
            view.hideLoading()
            view.onFavoriteTeamDataReceived(teamList)
        }
    }
}