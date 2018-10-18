package com.homework.mhafidhabdulaziz.football_apps.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var mFragmentList = arrayListOf<Fragment>()
    var mFragmentTitleList = arrayListOf<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}