package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayersdetail

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.PlayersDetailDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamPlayersDetailPresenter(private val view: TeamPlayersDetailView,
                                 private val apiRepository: FootBallApiRepository,
                                 private val gson: Gson,
                                 private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerData(id: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.getPlayerDetail(id)), PlayersDetailDto::class.java)
            }

            view.onReceivedPlayerData(data.await().player[0])
            view.hideLoading()
        }
    }
}