package com.homework.mhafidhabdulaziz.football_apps.service.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
data class TeamsDto(
        @SerializedName("teams") var teams: List<Team>
)