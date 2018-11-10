package com.homework.mhafidhabdulaziz.football_apps.presentation.teams

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
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
        setHasOptionsMenu(true)

        league_spinner.adapter = CommonUtils.getLeagueSpinner(context!!)

        mAdapter = TeamItemAdapter(teams)
        team_recycler.adapter = mAdapter

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = TeamPresenter(this, request, gson)

        league_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = league_spinner.selectedItem.toString()
                mPresenter.getTeamList(CommonUtils.getSelectedLeague(context!!, leagueName))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        team_swipe_layout.onRefresh {
            mPresenter.getTeamList(CommonUtils.getSelectedLeague(context!!, leagueName))
        }

        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        team_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        team_recycler.layoutManager = mLinearLayoutManager
    }

    override fun showLoading() {
        shimmer_team_container.startShimmer()
        shimmer_team_container.visibility = View.VISIBLE
        team_recycler.visibility = View.GONE
    }

    override fun hideLoading() {
        shimmer_team_container.stopShimmer()
        shimmer_team_container.visibility = View.GONE
        team_recycler.visibility = View.VISIBLE
    }

    override fun onReceivedTeamList(teamData: List<Team>) {
        team_swipe_layout.isRefreshing = false
        teams.clear()
        teams.addAll(teamData)
        mAdapter.notifyDataSetChanged()
        runLayoutAnimation()
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(team_recycler.context, R.anim.layout_slide_from_bottom)

        team_recycler.layoutAnimation = controller
        team_recycler.adapter?.notifyDataSetChanged()
        team_recycler.scheduleLayoutAnimation()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.title_search)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String): Boolean {
                mPresenter.searchTeam(keyword)
                return false
            }

            override fun onQueryTextChange(keyword: String): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity?.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            mPresenter.getTeamList("4328")
            true
        }
    }

}