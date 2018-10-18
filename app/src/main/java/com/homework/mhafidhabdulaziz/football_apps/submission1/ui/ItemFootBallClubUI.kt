package com.homework.mhafidhabdulaziz.football_apps.submission1.ui

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.homework.mhafidhabdulaziz.football_apps.R
import org.jetbrains.anko.*

/**
 * Created by mhafidhabdulaziz on 15/10/18.
 */
class ItemFootBallClubUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            lparams(matchParent, wrapContent)
            padding = dip(16)

            imageView {
                id = R.id.club_image_id

            }.lparams(dip(50), dip(50))

            textView {
                id = R.id.club_name_id
            }.lparams(matchParent, wrapContent) {
                margin = dip(10)
                gravity = Gravity.CENTER_VERTICAL
            }

        }
    }
}