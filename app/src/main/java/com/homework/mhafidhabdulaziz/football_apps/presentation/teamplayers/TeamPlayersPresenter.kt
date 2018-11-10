package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayers

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.PlayersDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamPlayersPresenter(private val view: TeamPlayersView,
                           private val apiRepository: FootBallApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(id: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.getAllPlayers(id)), PlayersDto::class.java)
            }

            view.onReceivedPlayerList(data.await().player)
            view.hideLoading()
        }
    }
}