package com.homework.mhafidhabdulaziz.football_apps.view.fragments

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
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.LastMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.LastMatchView
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import com.homework.mhafidhabdulaziz.football_apps.view.adapter.MatchScheduleItemAdapter
import kotlinx.android.synthetic.main.fragment_last_match_schedule.*

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class LastMatchScheduleFragment : Fragment(), LastMatchView {

    lateinit var presenter: LastMatchPresenter
    lateinit var mAdapter: MatchScheduleItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match_schedule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = LastMatchPresenter(activity!!.application)
        onAttachView()


        val mLinearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context!!, mLinearLayoutManager.orientation)
        last_match_recycler.addItemDecoration(dividerItemDecoration)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        last_match_recycler.layoutManager = mLinearLayoutManager
    }

    override fun onLastMatchDataReceived(response: String) {
        shimmer_last_match_container.stopShimmer()
        shimmer_last_match_container.visibility = View.GONE
        last_match_recycler.visibility = View.VISIBLE
        val lastMatchData = Gson().fromJson<MatchScheduleDto>(response, MatchScheduleDto::class.java)
        mAdapter = MatchScheduleItemAdapter(lastMatchData.events.toMutableList())
        last_match_recycler.adapter = mAdapter
        runLayoutAnimation()
    }

    override fun onAttachView() {
        shimmer_last_match_container.startShimmer()
        shimmer_last_match_container.visibility = View.VISIBLE
        last_match_recycler.visibility = View.GONE
        presenter.onAttach(this)
        presenter.requestLastMatchScheduleData("4328")
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(last_match_recycler.context, R.anim.layout_slide_from_bottom)

        last_match_recycler.layoutAnimation = controller
        last_match_recycler.adapter?.notifyDataSetChanged()
        last_match_recycler.scheduleLayoutAnimation()
    }
}