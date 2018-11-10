package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayers.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Player
import kotlinx.android.synthetic.main.item_team_players.view.*

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamPlayersItemAdapter(var player: MutableList<Player>) : RecyclerView.Adapter<TeamPlayersItemAdapter.TeamPlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayersViewHolder {
        return TeamPlayersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team_players, parent, false))
    }

    override fun onBindViewHolder(holder: TeamPlayersViewHolder, position: Int) {
        holder.bindItem(player[position])
    }

    override fun getItemCount(): Int = player.size

    inner class TeamPlayersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(items: Player) {
            itemView.player_name.text = items.strPlayer
            itemView.player_position.text = items.strPosition
            Glide.with(itemView.context).load(items.strCutout).into(itemView.player_image)

            itemView.setOnClickListener {
                //                itemView.context.startActivity<TeamPlayersDetailActivity>(TeamPlayersDetailActivity.EXTRA_PLAYER to items)
            }
        }
    }
}