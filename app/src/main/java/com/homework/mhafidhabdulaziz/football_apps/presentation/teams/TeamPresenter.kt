package com.homework.mhafidhabdulaziz.football_apps.presentation.teams

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 * NOTE : The class is build as following Dicoding module and exercise
 * So, it may be familiar
 */
class TeamPresenter(private val view: TeamView,
                    private val apiRepository: FootBallApiRepository,
                    private val gson: Gson) {

    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(FootBallClubRestApi.getTeams(league)),
                    TeamsDto::class.java
            )

            uiThread {
                view.hideLoading()
                view.onReceivedTeamList(data.teams)
            }
        }
    }
}