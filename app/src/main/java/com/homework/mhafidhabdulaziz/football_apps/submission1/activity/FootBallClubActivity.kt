package com.homework.mhafidhabdulaziz.football_apps.submission1.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.service.entity.FootBallClubDto
import com.homework.mhafidhabdulaziz.football_apps.submission1.adapter.FootBallClubItemAdapter
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.verticalLayout

/**
 * Created by mhafidhabdulaziz on 15/10/18.
 */
class FootBallClubActivity : AppCompatActivity() {

    var footballClubItem: MutableList<FootBallClubDto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeData()

        verticalLayout {
            lparams(matchParent, matchParent)
            orientation = LinearLayout.VERTICAL

            recyclerView {
                lparams(matchParent, matchParent)
                layoutManager = LinearLayoutManager(context)
                adapter = FootBallClubItemAdapter(footballClubItem) { club ->
                    startActivity<FootBallClubDetailActivity>(FootBallClubDetailActivity.EXTRA_POSITION to club)
                }
            }
        }
    }

    private fun initializeData() {
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val details = resources.getStringArray(R.array.club_details)

        footballClubItem.clear()

        for (position in name.indices) {
            footballClubItem.add(FootBallClubDto(name[position], image.getResourceId(position, 0), details[position]))
        }

        image.recycle()

    }
}
