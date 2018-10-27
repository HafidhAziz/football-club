package com.homework.mhafidhabdulaziz.football_apps.presentation.favoriteteam

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.local.db.TeamFavorite
import com.homework.mhafidhabdulaziz.football_apps.presentation.teams.adapter.TeamItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * Created by M Hafidh Abdul Aziz on 10/27/2018.
 */
class FavoriteTeamFragment: Fragment(), FavoriteTeamView {

    lateinit var mPresenter: FavoriteTeamPresenter
    lateinit var mAdapter: TeamItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = FavoriteTeamPresenter(this, request, gson)
        getTeamDataFromDB()

        fav_swipe_layout.onRefresh {
            getTeamDataFromDB()
        }

        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        fav_team_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fav_team_recycler.layoutManager = mLinearLayoutManager
    }

    override fun onFavoriteTeamDataReceivedFromDB(favTeamDataFromDB: List<TeamFavorite>) {
        mPresenter.getTeamDataFavorite(favTeamDataFromDB)
    }

    override fun onFavoriteTeamDataReceived(favTeamData: List<Team>) {
        fav_swipe_layout.isRefreshing = false
        mAdapter = TeamItemAdapter(favTeamData.toMutableList())
        fav_team_recycler.adapter = mAdapter
    }

    private fun getTeamDataFromDB() {
        mPresenter.selectTeamDataFromDB(context!!)
    }

    override fun onResume() {
        super.onResume()
        getTeamDataFromDB()
    }

    override fun showLoading() {
        showShimmer()
    }

    override fun hideLoading() {
        hideShimmer()
    }

    private fun hideShimmer() {
        shimmer_fav_team_container.stopShimmer()
        shimmer_fav_team_container.visibility = View.GONE
        fav_team_recycler.visibility = View.VISIBLE
    }

    private fun showShimmer() {
        shimmer_fav_team_container.startShimmer()
        shimmer_fav_team_container.visibility = View.VISIBLE
        fav_team_recycler.visibility = View.GONE
    }
}