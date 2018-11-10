package com.homework.mhafidhabdulaziz.football_apps.presentation.search

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.SearchMatchDto
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class SearchMatchSchedulePresenter(private val view: SearchMatchScheduleView,
                                   private val apiRepository: FootBallApiRepository,
                                   private val gson: Gson,
                                   private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun searchMatchSchedule(keyword: String?){
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(FootBallClubRestApi.searchMatchSchedule(keyword)), SearchMatchDto::class.java)
            }

            view.onSearchMatchDataReceived(data.await().events)
            view.hideLoading()
        }
    }
}