package com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.adapter.MatchScheduleItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class FavoriteListFragment : Fragment(), FavoriteMatchView {

    lateinit var mPresenter: FavoriteMatchPresenter
    lateinit var mAdapter: MatchScheduleItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = FavoriteMatchPresenter(this, request, gson)
        getMatchDataFromDB()

        fav_swipe_layout.onRefresh {
            getMatchDataFromDB()
        }

        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        fav_match_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fav_match_recycler.layoutManager = mLinearLayoutManager
    }

    override fun onFavoriteMatchDataReceivedFromDB(favMatchDataFromDB: List<MatchFavorite>) {
        mPresenter.getMatchDataFavorite(favMatchDataFromDB)
    }

    override fun onFavoriteMatchDataReceived(favMatchData: List<Event>) {
        fav_swipe_layout.isRefreshing = false
        mAdapter = MatchScheduleItemAdapter(favMatchData.toMutableList())
        fav_match_recycler.adapter = mAdapter
    }

    private fun getMatchDataFromDB() {
        mPresenter.selectMatchDataFromDB(context!!)
    }

    override fun onResume() {
        super.onResume()
        getMatchDataFromDB()
    }

    override fun showLoading() {
        showShimmer()
    }

    override fun hideLoading() {
        hideShimmer()
    }

    private fun hideShimmer() {
        shimmer_fav_match_container.stopShimmer()
        shimmer_fav_match_container.visibility = View.GONE
        fav_match_recycler.visibility = View.VISIBLE
    }

    private fun showShimmer() {
        shimmer_fav_match_container.startShimmer()
        shimmer_fav_match_container.visibility = View.VISIBLE
        fav_match_recycler.visibility = View.GONE
    }
}