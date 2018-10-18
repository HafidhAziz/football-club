package com.homework.mhafidhabdulaziz.football_apps.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
interface FootBallClubRestApi {

    @GET("eventspastleague.php")
    fun getLastMatchSchedule(@Query("id") id:String) : Call<String>

    @GET("eventsnextleague.php")
    fun getNextMatchSchedule(@Query("id") id:String) : Call<String>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") id:String) : Call<String>

    @GET("lookup_all_teams.php")
    fun getAllTeams(@Query("id") id:String) : Call<String>

    @GET("lookupplayer.php")
    fun getPlayerDetail(@Query("id") id:String?) : Call<String>

    @GET("lookup_all_players.php")
    fun getAllPlayers(@Query("id") id:String?) : Call<String>

    @GET("lookupevent.php")
    fun getMatchEventById(@Query("id") id:String) : Call<String>

}