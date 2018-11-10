package com.homework.mhafidhabdulaziz.football_apps.presentation.teamoverview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.teamDetail.TeamDetailActivity
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import kotlinx.android.synthetic.main.fragment_team_overview.*

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataTeam: Team? = arguments?.getParcelable(TeamDetailActivity.EXTRA_TEAM)
        overview.text = dataTeam?.strDescriptionEN
    }

}