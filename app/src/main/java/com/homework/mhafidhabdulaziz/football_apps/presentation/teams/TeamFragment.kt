package com.homework.mhafidhabdulaziz.football_apps.presentation.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.common.CommonUtils
import com.homework.mhafidhabdulaziz.football_apps.presentation.teams.adapter.TeamItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 * NOTE : The class is build as following Dicoding module and exercise
 * So, it may be familiar
 */
class TeamFragment : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var mPresenter: TeamPresenter
    private lateinit var mAdapter: TeamItemAdapter
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        league_spinner.adapter = CommonUtils.getLeagueSpinner(context!!)

        mAdapter = TeamItemAdapter(teams)
        team_recycler.adapter = mAdapter

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = TeamPresenter(this, request, gson)

        league_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = league_spinner.selectedItem.toString()
                mPresenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        team_swipe_layout.onRefresh {
            mPresenter.getTeamList(leagueName)
        }
    }

    override fun showLoading() {
        shimmer_team_container.startShimmer()
        shimmer_team_container.visibility = View.VISIBLE
        team_recycler.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        shimmer_team_container.stopShimmer()
        shimmer_team_container.visibility = View.GONE
        team_recycler.visibility = View.GONE
    }

    override fun onReceivedTeamList(data: List<Team>) {
        team_swipe_layout.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        mAdapter.notifyDataSetChanged()
    }

}