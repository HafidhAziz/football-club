package com.homework.mhafidhabdulaziz.football_apps.presentation.teams

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 * NOTE : The class is build as following Dicoding module and exercise
 * So, it may be familiar
 */
class TeamPresenter(private val view: TeamView,
                    private val apiRepository: FootBallApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(FootBallClubRestApi.getTeams(league)),
                        TeamsDto::class.java)
            }

            view.onReceivedTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}