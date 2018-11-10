package com.homework.mhafidhabdulaziz.football_apps.presentation.teamDetail

import android.content.Context
import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.local.database
import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
class TeamDetailPresenter(private val view: TeamDetailView) {

    fun insertTeamDataToFavorite(context: Context, teamId: String, badgeTeamUrl: String) {
        context.database.use {
            insert("TABLE_TEAM_FAV", "TEAM_ID" to teamId, "URL_TEAM_BADGE" to badgeTeamUrl)
        }

    }

    fun deleteTeamDataToFavorite(context: Context, teamId: String) {
        context.database.use {
            delete("TABLE_TEAM_FAV", "(TEAM_ID = {id})", "id" to teamId)
        }
    }

    fun selectTeamDataFromFavorite(context: Context, teamId: String) {
        return context.database.use {
            val result = select("TABLE_TEAM_FAV").whereArgs("(TEAM_ID = {id})", "id" to teamId)
            view.onTeamFavoriteDataReceived(result.parseList(classParser()))
        }
    }
}