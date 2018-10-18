package com.homework.mhafidhabdulaziz.football_apps.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.common.CommonUtils
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import com.homework.mhafidhabdulaziz.football_apps.view.activity.MatchScheduleDetailActivity
import kotlinx.android.synthetic.main.item_match_schedule.view.*
import org.jetbrains.anko.startActivity

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class MatchScheduleItemAdapter(var mData: MutableList<Event>) : RecyclerView.Adapter<MatchScheduleItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match_schedule, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(items: Event) {
            itemView.date_match.text = items.dateEvent.let { CommonUtils.getFormattedDate(it!!) }
            itemView.home_team.text = items.strHomeTeam
            itemView.home_score.text = items.intHomeScore
            itemView.away_team.text = items.strAwayTeam
            itemView.away_score.text = items.intAwayScore

            itemView.setOnClickListener {
                itemView.context.startActivity<MatchScheduleDetailActivity>(MatchScheduleDetailActivity.EXTRA_EVENTS to items)
            }

        }
    }
}