package com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch

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
import kotlinx.android.synthetic.main.fragment_next_match_schedule.*
import org.jetbrains.anko.support.v4.onRefresh
import java.util.*

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class NextMatchScheduleFragment : Fragment(), NextMatchView, MatchScheduleItemAdapter.AccessCalendarListener {

    private var matches: MutableList<Event> = mutableListOf()
    private lateinit var mPresenter: NextMatchPresenter
    private lateinit var mAdapter: MatchScheduleItemAdapter
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next_match_schedule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        league_spinner.adapter = CommonUtils.getLeagueSpinner(context!!)

        mAdapter = MatchScheduleItemAdapter(matches, context!!, true)
        mAdapter.accessCalendarListener = this
        next_match_recycler.adapter = mAdapter

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = NextMatchPresenter(this, request, gson)
        mPresenter.requestNextMatchScheduleData(Constants.DEFAULT_LEAGUE_MATCH_ID)

        league_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = league_spinner.selectedItem.toString()
                mPresenter.requestNextMatchScheduleData(CommonUtils.getSelectedLeague(context!!, leagueName))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        next_swipe_layout.onRefresh {
            mPresenter.requestNextMatchScheduleData(CommonUtils.getSelectedLeague(context!!, leagueName))
        }


        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        next_match_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        next_match_recycler.layoutManager = mLinearLayoutManager
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(next_match_recycler.context, R.anim.layout_slide_from_bottom)

        next_match_recycler.layoutAnimation = controller
        next_match_recycler.adapter?.notifyDataSetChanged()
        next_match_recycler.scheduleLayoutAnimation()
    }

    override fun showLoading() {
        showShimmer()
    }

    override fun hideLoading() {
        hideShimmer()
    }

    override fun onNextMatchDataReceived(matchData: List<Event>) {
        next_swipe_layout.isRefreshing = false
        matches.clear()
        matches.addAll(matchData)
        mAdapter.notifyDataSetChanged()
        runLayoutAnimation()
    }

    private fun hideShimmer() {
        shimmer_next_match_container.stopShimmer()
        shimmer_next_match_container.visibility = View.GONE
        next_match_recycler.visibility = View.VISIBLE
    }

    private fun showShimmer() {
        shimmer_next_match_container.startShimmer()
        shimmer_next_match_container.visibility = View.VISIBLE
        next_match_recycler.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            123 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(main_layout, "Now You can added event to calendar", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(main_layout, "You can not added event to calendar", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermissionCalendar(date: Date, homeTeam: String, awayTeam: String, eventId: String) {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR), 123)
        } else if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            addToCalendar(date, homeTeam, awayTeam, eventId)
        }
    }

    @SuppressLint("MissingPermission")
    private fun addToCalendar(date: Date, homeTeam: String, awayTeam: String, eventId: String) {
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.time)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.time)
                .putExtra(CalendarContract.Events.TITLE, "Football Schedule")
                .putExtra(CalendarContract.Events.DESCRIPTION, "$homeTeam VS $awayTeam")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(CalendarContract.Events.CALENDAR_ID, eventId)
        startActivity(intent)
    }

    override fun onAccessCalendarListener(date: Date, homeTeam: String, awayTeam: String, eventId: String) {
        checkPermissionCalendar(date, homeTeam, awayTeam, eventId)
    }
}