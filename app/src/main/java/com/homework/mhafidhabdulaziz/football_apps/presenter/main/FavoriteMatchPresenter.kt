package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.FootBallClubApplication
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presenter.base.Presenter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class FavoriteMatchPresenter @Inject constructor(application: Application) : Presenter<FavoriteMatchView> {

    @Inject
    lateinit var retrofit: Retrofit
    private var favMatchView: FavoriteMatchView? = null

    init {
        (application as FootBallClubApplication).getNetComponent()?.inject(this)
    }

    override fun onAttach(view: FavoriteMatchView) {
        favMatchView = view
    }

    override fun onDetach() {
        favMatchView = null
    }

    fun selectMatchDataFromDB(context: Context) {
        return context.database.use {
            val result = select("TABLE_FAVORITE")
            if (favMatchView != null) {
                favMatchView!!.onFavoriteMatchDataReceivedFromDB(result.parseList(classParser()))
            }
        }
    }

    fun getMatchDataFavorite(favMatchData: List<MatchFavorite>) {
        val eventList: MutableList<Event> = mutableListOf()
        for (favMatchEvent in favMatchData) {
            val request = retrofit.create<FootBallClubRestApi>(FootBallClubRestApi::class.java).getMatchEventById(favMatchEvent.idEvent)

            request.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val events = Gson().fromJson<MatchScheduleDto>(response.body()!!, MatchScheduleDto::class.java)
                    eventList.add(events.events[0])
                    if (favMatchView != null) {
                        favMatchView!!.onFavoriteMatchDataReceived(eventList)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }
            })
        }

        if (favMatchData.isEmpty()) {
            if (favMatchView != null) {
                favMatchView!!.onFavoriteMatchDataReceived(eventList)
            }
        }
    }
}