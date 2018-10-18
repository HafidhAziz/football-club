package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

class LastMatchPresenter(private val view: LastMatchView,
                         private val apiRepository: FootBallApiRepository,
                         private val gson: Gson) {

    fun requestLastMatchScheduleData(id: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.getLastMatchSchedule(id)), MatchScheduleDto::class.java)

            uiThread {
                view.hideLoading()
                if (data != null && data.events != null) {
                    view.onLastMatchDataReceived(data.events)
                }
            }
        }
    }
}
