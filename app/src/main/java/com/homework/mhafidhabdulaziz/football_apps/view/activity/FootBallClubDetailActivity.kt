package com.homework.mhafidhabdulaziz.football_apps.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.homework.mhafidhabdulaziz.football_apps.service.entity.FootBallClubDto
import com.homework.mhafidhabdulaziz.football_apps.view.ui.ActivityFootBallClubDetailUI
import org.jetbrains.anko.setContentView

/**
 * Created by mhafidhabdulaziz on 15/10/18.
 */
class FootBallClubDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_POSITION = "EXTRA_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val list = intent.getParcelableExtra<FootBallClubDto>(EXTRA_POSITION)

        ActivityFootBallClubDetailUI(list).setContentView(this)

    }
}