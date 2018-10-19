package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

class LastMatchPresenter(private val view: LastMatchView,
                         private val apiRepository: FootBallApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun requestLastMatchScheduleData(id: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.getLastMatchSchedule(id)), MatchScheduleDto::class.java)
            }

            if (data != null && data.await().events != null) {
                view.onLastMatchDataReceived(data.await().events)
            }
            view.hideLoading()
        }
    }
}
