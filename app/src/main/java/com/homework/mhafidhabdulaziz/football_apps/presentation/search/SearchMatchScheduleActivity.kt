package com.homework.mhafidhabdulaziz.football_apps.presentation.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.adapter.MatchScheduleItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import kotlinx.android.synthetic.main.activity_search_match_schedule.*

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class SearchMatchScheduleActivity : AppCompatActivity(), SearchMatchScheduleView {

    private var matches: MutableList<Event> = mutableListOf()
    lateinit var mPresenter: SearchMatchSchedulePresenter
    lateinit var mAdapter: MatchScheduleItemAdapter

    companion object {
        const val KEYWORD = "EXTRA_KEYWORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match_schedule)

        mAdapter = MatchScheduleItemAdapter(matches, this, false)
        search_match_recycler.adapter = mAdapter

        val keyword = intent.getStringExtra(KEYWORD)
        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = SearchMatchSchedulePresenter(this, request, gson)
        mPresenter.searchMatchSchedule(keyword)

        val mLinearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, mLinearLayoutManager.orientation)
        search_match_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        search_match_recycler.layoutManager = mLinearLayoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.title_search)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                mPresenter.searchMatchSchedule(keyword)
                return false
            }
        })

        searchView?.setOnCloseListener {
            finish()
            true
        }

        return true
    }

    override fun showLoading() {
        shimmer_search_match_container.startShimmer()
        shimmer_search_match_container.visibility = View.VISIBLE
        search_match_recycler.visibility = View.GONE
    }

    override fun hideLoading() {
        shimmer_search_match_container.stopShimmer()
        shimmer_search_match_container.visibility = View.GONE
        search_match_recycler.visibility = View.VISIBLE
    }

    override fun onSearchMatchDataReceived(matchData: List<Event>) {
        matches.clear()
        matches.addAll(matchData)
        mAdapter.notifyDataSetChanged()
        runLayoutAnimation()
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(search_match_recycler.context, R.anim.layout_slide_from_bottom)

        search_match_recycler.layoutAnimation = controller
        search_match_recycler.adapter?.notifyDataSetChanged()
        search_match_recycler.scheduleLayoutAnimation()
    }
}