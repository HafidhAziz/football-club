package com.homework.mhafidhabdulaziz.football_apps.presentation.teamDetail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.local.db.TeamFavorite
import com.homework.mhafidhabdulaziz.football_apps.presentation.ViewPagerAdapter
import com.homework.mhafidhabdulaziz.football_apps.presentation.teamoverview.TeamOverviewFragment
import com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayers.TeamPlayersFragment
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import kotlinx.android.synthetic.main.activity_team_details.*

/**
 * Created by M Hafidh Abdul Aziz on 10/28/2018.
 */
class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    lateinit var mPresenter: TeamDetailPresenter
    lateinit var team: Team
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private var mPagerAdapter: ViewPagerAdapter? = null

    companion object {
        const val EXTRA_TEAM = "EXTRA_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        team = intent.getParcelableExtra(EXTRA_TEAM)
        supportActionBar?.title = team.strTeam
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setPagerAdapter(supportFragmentManager)

        mPresenter = TeamDetailPresenter(this)
        onAttachView()

        mappingData(team)
    }

    private fun setPagerAdapter(fragmentManager: FragmentManager) {
        mPagerAdapter = ViewPagerAdapter(fragmentManager)

        val bundle = Bundle()
        bundle.putParcelable(EXTRA_TEAM, team)

        val teamOverviewFragment = TeamOverviewFragment()
        val teamPlayersFragment = TeamPlayersFragment()
        teamOverviewFragment.arguments = bundle
        teamPlayersFragment.arguments = bundle

        mPagerAdapter?.addFragment(teamOverviewFragment, getString(R.string.title_overview))
        mPagerAdapter?.addFragment(teamPlayersFragment, getString(R.string.title_players))
        view_pager.adapter = mPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_item, menu)
        menuItem = menu
        checkIsFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkIsFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_line_active)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_line_inactive)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let {
            when (it) {
                android.R.id.home -> finish()
                R.id.favorite -> {
                    if (!isFavorite) {
                        mPresenter.insertTeamDataToFavorite(this, team.idTeam, team.strTeamBadge)
                        isFavorite = !isFavorite
                        Snackbar.make(main_layout, R.string.title_add, Snackbar.LENGTH_SHORT).show()
                    } else {
                        mPresenter.deleteTeamDataToFavorite(this, team.idTeam)
                        isFavorite = !isFavorite
                        Snackbar.make(main_layout, R.string.title_removed, Snackbar.LENGTH_SHORT).show()
                    }
                    checkIsFavorite()
                }
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onAttachView() {
        mPresenter.selectTeamDataFromFavorite(this, team.idTeam)
    }

    private fun mappingData(team: Team?) {
        Glide.with(applicationContext)
                .load(team?.strTeamBadge)
                .into(team_badge)

        team_name.text = team?.strTeam
        team_year.text = team?.intFormedYear
        team_stadium.text = team?.strStadium
    }

    override fun onTeamFavoriteDataReceived(favoriteData: List<TeamFavorite>) {
        if (!favoriteData.isEmpty()) isFavorite = true
    }

}