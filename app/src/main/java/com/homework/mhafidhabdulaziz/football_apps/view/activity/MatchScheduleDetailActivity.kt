package com.homework.mhafidhabdulaziz.football_apps.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.common.CommonUtils
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.MatchScheduleDetailsPresenter
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.MatchScheduleDetailsView
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamDetailDto
import kotlinx.android.synthetic.main.activity_match_schedule_details.*

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class MatchScheduleDetailActivity : AppCompatActivity(), MatchScheduleDetailsView {

    lateinit var presenter: MatchScheduleDetailsPresenter
    lateinit var event: Event
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    companion object {
        val EXTRA_EVENTS = "EXTRA_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_schedule_details)
        event = intent.getParcelableExtra(EXTRA_EVENTS)
        supportActionBar?.title = event.strEvent
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mappingData(event)

        presenter = MatchScheduleDetailsPresenter(this.application)
        onAttachView()
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
                        presenter.insertMatchDataToFavorite(this, event.idEvent, event.idHomeTeam, event.idAwayTeam)
                        isFavorite = !isFavorite
                        Snackbar.make(main_layout, R.string.title_add, Snackbar.LENGTH_SHORT).show()
                    } else {
                        presenter.deleteMatchDataToFavorite(this, event.idEvent)
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

    private fun mappingData(event: Event?) {
        date_match_details.text = event?.dateEvent?.let { CommonUtils.getFormattedDate(it) }
        home_team_name.text = event?.strHomeTeam
        home_team_scorer.text = event?.strHomeGoalDetails?.let { CommonUtils.getSeparatorNoSpace(it) }
        home_team_score.text = event?.intHomeScore
        away_team_name.text = event?.strAwayTeam
        away_team_scorer.text = event?.strAwayGoalDetails?.let { CommonUtils.getSeparatorNoSpace(it) }
        away_team_score.text = event?.intAwayScore
        home_gk.text = event?.strHomeLineupGoalkeeper?.let { CommonUtils.getSeparator(it) }
        home_defender.text = event?.strHomeLineupDefense?.let { CommonUtils.getSeparator(it) }
        home_midfield.text = event?.strHomeLineupMidfield?.let { CommonUtils.getSeparator(it) }
        home_forward.text = event?.strHomeLineupForward?.let { CommonUtils.getSeparator(it) }
        home_subtitutes.text = event?.strHomeLineupSubstitutes?.let { CommonUtils.getSeparator(it) }
        away_gk.text = event?.strAwayLineupGoalkeeper?.let { CommonUtils.getSeparator(it) }
        away_defender.text = event?.strAwayLineupDefense?.let { CommonUtils.getSeparator(it) }
        away_midfield.text = event?.strAwayLineupMidfield?.let { CommonUtils.getSeparator(it) }
        away_forward.text = event?.strAwayLineupForward?.let { CommonUtils.getSeparator(it) }
        away_subtitutes.text = event?.strAwayLineupSubstitutes?.let { CommonUtils.getSeparator(it) }
    }

    override fun onTeamDetailDataReceived(response: String, isHomeTeam: Boolean) {
        val teamDetailData = Gson().fromJson<TeamDetailDto>(response, TeamDetailDto::class.java)
        teamDetailData.let { detail ->
            detail.teams.let { team ->
                if (isHomeTeam) {
                    Glide.with(this)
                            .load(team.get(0).strTeamBadge)
                            .into(home_team_logo)
                } else {
                    Glide.with(this)
                            .load(team.get(0).strTeamBadge)
                            .into(away_team_logo)
                }
            }
        }
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.requestTeamDetailData(event.idHomeTeam, true)
        presenter.requestTeamDetailData(event.idAwayTeam, false)
        presenter.selectMatchDataFromFavorite(this, event.idEvent)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun onMatchFavoriteDataReceived(favoriteData: List<MatchFavorite>) {
        if (!favoriteData.isEmpty()) isFavorite = true
    }

}