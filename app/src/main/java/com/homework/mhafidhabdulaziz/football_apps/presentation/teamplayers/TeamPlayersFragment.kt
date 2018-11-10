package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.teamDetail.TeamDetailActivity
import com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayers.adapter.TeamPlayersItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Player
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import kotlinx.android.synthetic.main.fragment_team_players.*
import org.jetbrains.anko.support.v4.onRefresh


/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamPlayersFragment : Fragment(), TeamPlayersView {

    private var player: MutableList<Player> = mutableListOf()
    private lateinit var mPresenter: TeamPlayersPresenter
    private lateinit var mAdapter: TeamPlayersItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataTeam: Team? = arguments?.getParcelable(TeamDetailActivity.EXTRA_TEAM)

        mAdapter = TeamPlayersItemAdapter(player)
        player_recycler.adapter = mAdapter

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = TeamPlayersPresenter(this, request, gson)
        if (dataTeam != null) mPresenter.getPlayerList(dataTeam.idTeam)

        player_swipe_layout.onRefresh {
            if (dataTeam != null) mPresenter.getPlayerList(dataTeam.idTeam)
        }

        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        player_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        player_recycler.layoutManager = mLinearLayoutManager
    }

    override fun showLoading() {
        shimmer_player_container.startShimmer()
        shimmer_player_container.visibility = View.VISIBLE
        player_recycler.visibility = View.GONE
    }

    override fun hideLoading() {
        shimmer_player_container.stopShimmer()
        shimmer_player_container.visibility = View.GONE
        player_recycler.visibility = View.VISIBLE
    }

    override fun onReceivedPlayerList(playerData: List<Player>) {
        player_swipe_layout.isRefreshing = false
        player.clear()
        player.addAll(playerData)
        mAdapter.notifyDataSetChanged()
        runLayoutAnimation()
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(player_recycler.context, R.anim.layout_slide_from_bottom)

        player_recycler.layoutAnimation = controller
        player_recycler.adapter?.notifyDataSetChanged()
        player_recycler.scheduleLayoutAnimation()
    }
}