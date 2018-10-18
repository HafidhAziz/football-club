package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import android.app.Application
import android.content.Context
import com.homework.mhafidhabdulaziz.football_apps.FootBallClubApplication
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.presenter.base.Presenter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class MatchScheduleDetailsPresenter @Inject constructor(application: Application) : Presenter<MatchScheduleDetailsView> {

    @Inject
    lateinit var retrofit: Retrofit
    private var matchDetailsView: MatchScheduleDetailsView? = null

    init {
        (application as FootBallClubApplication).getNetComponent()?.inject(this)
    }

    override fun onAttach(view: MatchScheduleDetailsView) {
        matchDetailsView = view
    }

    override fun onDetach() {
        matchDetailsView = null
    }

    fun requestTeamDetailData(id: String, isHomeTeam: Boolean) {
        makeAPICall(id, isHomeTeam)
    }

    private fun makeAPICall(id: String, isHomeTeam: Boolean) {
        val request = retrofit.create<FootBallClubRestApi>(FootBallClubRestApi::class.java).getTeamDetail(id)

        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (matchDetailsView != null) {
                    matchDetailsView!!.onTeamDetailDataReceived(response.body()!!, isHomeTeam)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

    fun insertMatchDataToFavorite(context: Context, eventId: String, homeTeamId: String, awayTeamId: String) {
        context.database.use {
            insert("TABLE_FAVORITE", "EVENT_ID" to eventId, "HOME_TEAM" to homeTeamId, "AWAY_TEAM" to awayTeamId)
        }

    }

    fun deleteMatchDataToFavorite(context: Context, eventId: String) {
        context.database.use {
            delete("TABLE_FAVORITE", "(EVENT_ID = {id})", "id" to eventId)
        }
    }

    fun selectMatchDataFromFavorite(context: Context, eventId: String) {
        return context.database.use {
            val result = select("TABLE_FAVORITE").whereArgs("(EVENT_ID = {id})", "id" to eventId)
            if (matchDetailsView != null) {
                matchDetailsView!!.onMatchFavoriteDataReceived(result.parseList(classParser()))
            }
        }
    }
}