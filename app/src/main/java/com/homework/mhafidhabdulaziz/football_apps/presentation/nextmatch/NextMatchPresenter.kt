package com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: FootBallApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun requestNextMatchScheduleData(id: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.getNextMatchSchedule(id)), MatchScheduleDto::class.java)
            }

            if (data != null && data.await().events != null) {
                view.onNextMatchDataReceived(data.await().events)
            }
            view.hideLoading()
        }
    }
}
