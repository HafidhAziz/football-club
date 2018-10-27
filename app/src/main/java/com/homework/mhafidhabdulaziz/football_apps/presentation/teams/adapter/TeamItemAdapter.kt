package com.homework.mhafidhabdulaziz.football_apps.presentation.teams.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.presentation.teamDetail.TeamDetailActivity
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.*

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 * NOTE : The class is build as following Dicoding module and exercise
 * So, it may be familiar
 */
class TeamItemAdapter(var teams: MutableList<Team>) : RecyclerView.Adapter<TeamItemAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position])
    }

    override fun getItemCount(): Int = teams.size

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(items: Team) {
            itemView.team_name.text = items.strTeam
            Glide.with(itemView.context).load(items.strTeamBadge).into(itemView.team_badge)

            itemView.setOnClickListener {
                itemView.context.startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_TEAM to items)
            }
        }
    }

}

