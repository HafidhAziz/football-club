package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.SearchView
import android.view.*
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.ViewPagerAdapter
import com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch.NextMatchScheduleFragment
import com.homework.mhafidhabdulaziz.football_apps.presentation.search.SearchMatchScheduleActivity
import kotlinx.android.synthetic.main.fragment_match_schedule.*
import org.jetbrains.anko.startActivity


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
        setHasOptionsMenu(true)
    }

    private fun setPagerAdapter(fragmentManager: FragmentManager) {
        mPagerAdapter = ViewPagerAdapter(fragmentManager)

        mPagerAdapter?.addFragment(LastMatchScheduleFragment(), getString(R.string.title_last_match))
        mPagerAdapter?.addFragment(NextMatchScheduleFragment(), getString(R.string.title_next_match))
        view_pager.adapter = mPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.title_search)
        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                context?.startActivity<SearchMatchScheduleActivity>(SearchMatchScheduleActivity.KEYWORD to query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}