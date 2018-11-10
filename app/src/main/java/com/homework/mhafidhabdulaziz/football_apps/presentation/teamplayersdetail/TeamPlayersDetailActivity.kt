package com.homework.mhafidhabdulaziz.football_apps.presentation.teamplayersdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.R
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Player
import kotlinx.android.synthetic.main.activity_team_player_details.*

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamPlayersDetailActivity : AppCompatActivity(), TeamPlayersDetailView {

    lateinit var mPresenter: TeamPlayersDetailPresenter
    lateinit var player: Player

    companion object {
        const val EXTRA_PLAYER = "EXTRA_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_player_details)
        player = intent.getParcelableExtra(EXTRA_PLAYER)
        supportActionBar?.title = player.strPlayer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = FootBallApiRepository()
        val gson = Gson()
        mPresenter = TeamPlayersDetailPresenter(this, request, gson)
        mPresenter.getPlayerData(player.idPlayer)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let {
            when (it) {
                android.R.id.home -> finish()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mappingData(player: Player?) {
        Glide.with(this).load(player?.strCutout).into(player_image)
        player_name.text = player?.strPlayer
        player_position.text = player?.strPosition
        player_born.text = player?.dateBorn
        player_overview.text = player?.strDescriptionEN
    }

    override fun showLoading() {
        shimmer_player_detail_container.startShimmer()
        shimmer_player_detail_container.visibility = View.VISIBLE
        player_detail_view.visibility = View.GONE
    }

    override fun hideLoading() {
        shimmer_player_detail_container.stopShimmer()
        shimmer_player_detail_container.visibility = View.GONE
        player_detail_view.visibility = View.VISIBLE
    }

    override fun onReceivedPlayerData(playerData: Player) {
        mappingData(player)
        runLayoutAnimation()
    }

    private fun runLayoutAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(player_detail_view.context, R.anim.layout_slide_from_bottom)

        player_detail_view.layoutAnimation = controller
        player_detail_view.scheduleLayoutAnimation()
    }
}