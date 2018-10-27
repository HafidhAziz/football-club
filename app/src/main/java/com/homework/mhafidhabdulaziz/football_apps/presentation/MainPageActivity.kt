package com.homework.mhafidhabdulaziz.football_apps.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch.FavoriteListFragment
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.MatchScheduleFragment
import com.homework.mhafidhabdulaziz.football_apps.presentation.teams.TeamFragment
import kotlinx.android.synthetic.main.main_page_activity.*


/**
 * Created by M Hafidh Abdul Aziz on 10/17/2018.
 */
class MainPageActivity : AppCompatActivity() {
    var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page_activity)
        supportActionBar?.elevation = 0f

        bottom_tab.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_schedule -> {
                    if (firstTime || (R.id.action_schedule != getSelectedItem(bottom_tab))) {
                        firstTime = false
                        changeMainFragmentContent(MatchScheduleFragment())
                    }
                }
                R.id.action_club -> {
                    if (R.id.action_club != getSelectedItem(bottom_tab)) {
                        changeMainFragmentContent(TeamFragment())
                    }
                }
                R.id.action_favorite -> {
                    if (R.id.action_favorite != getSelectedItem(bottom_tab))
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

    private fun getSelectedItem(bottomNavigationView: BottomNavigationView): Int {
        val menu = bottomNavigationView.menu
        for (i in 0 until bottomNavigationView.menu.size()) {
            val menuItem = menu.getItem(i)
            if (menuItem.isChecked) {
                return menuItem.itemId
            }
        }
        return 0
    }
}