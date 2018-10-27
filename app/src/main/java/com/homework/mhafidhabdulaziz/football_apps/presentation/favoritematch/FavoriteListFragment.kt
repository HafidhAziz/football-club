package com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presentation.ViewPagerAdapter
import com.homework.mhafidhabdulaziz.football_apps.presentation.favoriteteam.FavoriteTeamFragment
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.adapter.MatchScheduleItemAdapter
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class FavoriteListFragment : Fragment() {

    private var mPagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPagerAdapter(childFragmentManager)
    }

    private fun setPagerAdapter(fragmentManager: FragmentManager) {
        mPagerAdapter = ViewPagerAdapter(fragmentManager)

        mPagerAdapter?.addFragment(FavoriteMatchFragment(), getString(R.string.title_match))
        mPagerAdapter?.addFragment(FavoriteTeamFragment(), getString(R.string.title_team))
        view_pager.adapter = mPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
}