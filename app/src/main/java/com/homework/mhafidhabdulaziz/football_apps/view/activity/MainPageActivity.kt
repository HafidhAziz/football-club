package com.homework.mhafidhabdulaziz.football_apps.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.view.fragments.FavoriteListFragment
import com.homework.mhafidhabdulaziz.football_apps.view.fragments.MatchScheduleFragment
import kotlinx.android.synthetic.main.main_page_activity.*

/**
 * Created by M Hafidh Abdul Aziz on 10/17/2018.
 */
class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page_activity)
        supportActionBar?.elevation = 0f

        bottom_tab.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_schedule -> {
                    changeMainFragmentContent(MatchScheduleFragment())
                }
                R.id.action_favorite -> {
                    changeMainFragmentContent(FavoriteListFragment())
                }
            }
            true
        }
        bottom_tab.selectedItemId = R.id.action_schedule
    }

    private fun changeMainFragmentContent(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }
}