package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.common.CommonUtils
import com.homework.mhafidhabdulaziz.football_apps.common.Constants
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.adapter.MatchScheduleItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import kotlinx.android.synthetic.main.fragment_last_match_schedule.*
import org.jetbrains.anko.support.v4.onRefresh
import java.util.*

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class LastMatchScheduleFragment : Fragment(), LastMatchView {

    private var matches: MutableList<Event> = mutableListOf()
    private lateinit var mPresenter: LastMatchPresenter
    private lateinit var mAdapter: MatchScheduleItemAdapter
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match_schedule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        league_spinner.adapter = CommonUtils.getLeagueSpinner(context!!)

        mAdapter = MatchScheduleItemAdapter(matches, context!!, false)
        last_match_recycler.adapter = mAdapter

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = LastMatchPresenter(this, request, gson)
        mPresenter.requestLastMatchScheduleData(Constants.DEFAULT_LEAGUE_MATCH_ID)

        league_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = league_spinner.selectedItem.toString()
                mPresenter.requestLastMatchScheduleData(CommonUtils.getSelectedLeague(context!!, leagueName))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        last_swipe_layout.onRefresh {
            mPresenter.requestLastMatchScheduleData(CommonUtils.getSelectedLeague(context!!, leagueName))
        }


        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        last_match_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        last_match_recycler.layoutManager = mLinearLayoutManager
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(last_match_recycler.context, R.anim.layout_slide_from_bottom)

        last_match_recycler.layoutAnimation = controller
        last_match_recycler.adapter?.notifyDataSetChanged()
        last_match_recycler.scheduleLayoutAnimation()
    }

    override fun showLoading() {
        showShimmer()
    }

    override fun hideLoading() {
        hideShimmer()
    }

    override fun onLastMatchDataReceived(matchData: List<Event>) {
        last_swipe_layout.isRefreshing = false
        matches.clear()
        matches.addAll(matchData)
        mAdapter.notifyDataSetChanged()
        runLayoutAnimation()
    }

    private fun hideShimmer() {
        shimmer_last_match_container.stopShimmer()
        shimmer_last_match_container.visibility = View.GONE
        last_match_recycler.visibility = View.VISIBLE
    }

    private fun showShimmer() {
        shimmer_last_match_container.startShimmer()
        shimmer_last_match_container.visibility = View.VISIBLE
        last_match_recycler.visibility = View.GONE
    }
}