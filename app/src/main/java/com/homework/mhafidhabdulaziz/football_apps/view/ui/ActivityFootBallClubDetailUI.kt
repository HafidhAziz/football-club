package com.homework.mhafidhabdulaziz.football_apps.view.ui

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.service.entity.FootBallClubDto
import com.homework.mhafidhabdulaziz.football_apps.view.activity.FootBallClubDetailActivity
import org.jetbrains.anko.*

/**
 * Created by mhafidhabdulaziz on 15/10/18.
 */
class ActivityFootBallClubDetailUI(var list: FootBallClubDto) : AnkoComponent<FootBallClubDetailActivity> {

    override fun createView(ui: AnkoContext<FootBallClubDetailActivity>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            lparams(matchParent, matchParent)

            imageView() {
                Glide.with(this).load(list.image).into(this)
                id = R.id.club_image_id
                padding = dip(10)

            }.lparams(dip(80), dip(80)) {
                topMargin = dip(5)
            }

            textView {
                id = R.id.club_name_id
                text = list.name
                textSize = sp(5).toFloat()
                gravity = Gravity.CENTER_HORIZONTAL
                padding = dip(10)
            }

            textView {
                id = R.id.club_details_id
                text = list.details
                gravity = Gravity.CENTER_HORIZONTAL
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                padding = dip(10)
            }

        }
    }

}