package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.ViewPagerAdapter
import com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch.NextMatchScheduleFragment
import kotlinx.android.synthetic.main.fragment_match_schedule.*


/**
 * Created by M Hafidh Abdul Aziz on 10/17/2018.
 */
class MatchScheduleFragment : Fragment() {

    private var mPagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPagerAdapter(childFragmentManager)
    }

    private fun setPagerAdapter(fragmentManager: FragmentManager) {
        mPagerAdapter = ViewPagerAdapter(fragmentManager)

        mPagerAdapter?.addFragment(LastMatchScheduleFragment(), getString(R.string.title_last_match))
        mPagerAdapter?.addFragment(NextMatchScheduleFragment(), getString(R.string.title_next_match))
        view_pager.adapter = mPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
}