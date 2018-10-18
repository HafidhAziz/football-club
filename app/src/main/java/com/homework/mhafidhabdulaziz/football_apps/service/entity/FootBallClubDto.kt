package com.homework.mhafidhabdulaziz.football_apps.service.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by mhafidhabdulaziz on 15/10/18.
 */
@Parcelize
data class FootBallClubDto(val name: String, val image: Int, val details: String) : Parcelable
