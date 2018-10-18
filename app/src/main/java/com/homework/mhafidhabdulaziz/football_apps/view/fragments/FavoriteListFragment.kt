package com.homework.mhafidhabdulaziz.football_apps.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.FavoriteMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.FavoriteMatchView
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import com.homework.mhafidhabdulaziz.football_apps.view.adapter.MatchScheduleItemAdapter
import kotlinx.android.synthetic.main.fragment_favorite_match.*

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class FavoriteListFragment : Fragment(), FavoriteMatchView {

    lateinit var presenter: FavoriteMatchPresenter
    lateinit var mAdapter: MatchScheduleItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = FavoriteMatchPresenter(activity!!.application)

        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        fav_match_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fav_match_recycler.layoutManager = mLinearLayoutManager
    }

    override fun onFavoriteMatchDataReceivedFromDB(favMatchDataFromDB: List<MatchFavorite>) {
        presenter.getMatchDataFavorite(favMatchDataFromDB)
    }

    override fun onFavoriteMatchDataReceived(favMatchData: List<Event>) {
        shimmer_fav_match_container.stopShimmer()
        shimmer_fav_match_container.visibility = View.GONE
        fav_match_recycler.visibility = View.VISIBLE
        mAdapter = MatchScheduleItemAdapter(favMatchData.toMutableList())
        fav_match_recycler.adapter = mAdapter
    }

    override fun onAttachView() {
        shimmer_fav_match_container.startShimmer()
        shimmer_fav_match_container.visibility = View.VISIBLE
        fav_match_recycler.visibility = View.GONE
        presenter.onAttach(this)
        presenter.selectMatchDataFromDB(context!!)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        onAttachView()
    }
}