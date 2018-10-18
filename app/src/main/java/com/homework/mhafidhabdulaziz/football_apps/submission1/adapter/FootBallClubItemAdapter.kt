package com.homework.mhafidhabdulaziz.football_apps.submission1.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.service.entity.FootBallClubDto
import com.homework.mhafidhabdulaziz.football_apps.submission1.ui.ItemFootBallClubUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by mhafidhabdulaziz on 15/10/18.
 */
class FootBallClubItemAdapter(var list: MutableList<FootBallClubDto>, var listener: (FootBallClubDto) -> Unit) : RecyclerView.Adapter<FootBallClubItemAdapter.FootballClubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballClubViewHolder {
        return FootballClubViewHolder(ItemFootBallClubUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FootballClubViewHolder, position: Int) {
        holder.bindItem(list[position], listener)
    }

    override fun getItemCount(): Int = list.size

    inner class FootballClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clubImageView: ImageView
        var clubNameTextView: TextView

        init {
            clubImageView = itemView.find<ImageView>(R.id.club_image_id)
            clubNameTextView = itemView.find<TextView>(R.id.club_name_id)
        }

        fun bindItem(items: FootBallClubDto, listener: (FootBallClubDto) -> Unit) {
            clubNameTextView.text = items.name
            Glide.with(itemView.context).load(items.image).into(clubImageView)
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }

}