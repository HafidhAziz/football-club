package com.homework.mhafidhabdulaziz.football_apps.service

import com.homework.mhafidhabdulaziz.football_apps.common.Constants

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
object FootBallClubRestApi {

    fun getTeams(league: String?): String {
        return Constants.SERVICE_BASE_URL + "/search_all_teams.php?l=" + league
    }

    fun getLastMatchSchedule(id: String?): String {
        return Constants.SERVICE_BASE_URL + "/eventspastleague.php?id=" + id
    }

    fun getNextMatchSchedule(id: String?): String {
        return Constants.SERVICE_BASE_URL + "/eventsnextleague.php?id=" + id
    }

    fun getMatchEventById(id: String?): String {
        return Constants.SERVICE_BASE_URL + "/lookupevent.php?id=" + id
    }

    fun getTeamDetail(id: String?): String {
        return Constants.SERVICE_BASE_URL + "/lookupteam.php?id=" + id
    }

    fun getAllTeams(id: String?) : String{
        return Constants.SERVICE_BASE_URL + "/lookup_all_teams.php?id=" + id
    }

    fun getPlayerDetail(id: String?) : String{
        return Constants.SERVICE_BASE_URL + "/lookupplayer.php?id=" + id
    }

    fun getAllPlayers(id: String?) : String{
        return Constants.SERVICE_BASE_URL + "/lookup_all_players.php?id=" + id
    }

}