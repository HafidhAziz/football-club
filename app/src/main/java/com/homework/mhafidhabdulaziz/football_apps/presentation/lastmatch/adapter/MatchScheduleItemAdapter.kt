package com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.common.CommonUtils
import com.homework.mhafidhabdulaziz.football_apps.presentation.matchdetail.MatchScheduleDetailActivity
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import kotlinx.android.synthetic.main.item_match_schedule.view.*
import org.jetbrains.anko.startActivity
import java.util.*


/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
class MatchScheduleItemAdapter(var mData: MutableList<Event>, val context: Context, val showNotificationIcon: Boolean) : RecyclerView.Adapter<MatchScheduleItemAdapter.ViewHolder>() {

    var accessCalendarListener: AccessCalendarListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match_schedule, parent, false)
        return ViewHolder(view, accessCalendarListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView: View, var accessCalendarListener: AccessCalendarListener? = null) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(items: Event) {
            if (!showNotificationIcon) {
                itemView.add_to_calendar.visibility = View.GONE
            }
            itemView.date_match.text = items.dateEvent.let { CommonUtils.getFormattedDate(it!!) }
            // 8 to avoid fail parsing time, in some cases of search match , api returns different value with get last and next match
            if(items.strTime != null && !items.strTime.isEmpty() && items.strTime.length > 8){
                itemView.time_match.visibility = View.VISIBLE
                itemView.time_match.text = items.strTime.let { CommonUtils.getFormattedTime(it) }
            }
            itemView.home_team.text = items.strHomeTeam
            itemView.home_score.text = items.intHomeScore
            itemView.away_team.text = items.strAwayTeam
            itemView.away_score.text = items.intAwayScore

            itemView.setOnClickListener {
                itemView.context.startActivity<MatchScheduleDetailActivity>(MatchScheduleDetailActivity.EXTRA_EVENTS to items)
            }

            itemView.add_to_calendar.setOnClickListener {
                accessCalendarListener?.onAccessCalendarListener(items.dateEvent!!, items.strHomeTeam, items.strAwayTeam, items.idEvent)
            }

        }
    }

    interface AccessCalendarListener {
        fun onAccessCalendarListener(date: Date, homeTeam: String, awayTeam: String, eventId: String)
    }
}