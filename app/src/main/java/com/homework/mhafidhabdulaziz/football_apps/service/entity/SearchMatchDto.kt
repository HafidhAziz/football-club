package com.homework.mhafidhabdulaziz.football_apps.service.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by M Hafidh Abdul Aziz on 11/10/2018.
 */
data class SearchMatchDto(
        @SerializedName("event") var events: List<Event>
)