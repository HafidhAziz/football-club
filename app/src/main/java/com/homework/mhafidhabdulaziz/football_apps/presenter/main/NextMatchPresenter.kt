package com.homework.mhafidhabdulaziz.football_apps.presenter.main

import android.app.Application
import com.homework.mhafidhabdulaziz.football_apps.FootBallClubApplication
import com.homework.mhafidhabdulaziz.football_apps.presenter.base.Presenter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

class NextMatchPresenter @Inject constructor(application: Application) : Presenter<NextMatchView> {

    @Inject
    lateinit var retrofit: Retrofit
    private var newsSourceView: NextMatchView? = null

    init {
        (application as FootBallClubApplication).getNetComponent()?.inject(this)
    }

    override fun onAttach(view: NextMatchView) {
        newsSourceView = view
    }

    override fun onDetach() {
        newsSourceView = null
    }

    fun requestNextMatchScheduleData(id: String) {
        makeAPICall(id)
    }

    private fun makeAPICall(id: String) {
        val request = retrofit.create<FootBallClubRestApi>(FootBallClubRestApi::class.java).getNextMatchSchedule(id)

        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (newsSourceView != null) {
                    newsSourceView!!.onNextMatchDataReceived(response.body()!!)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }
}
