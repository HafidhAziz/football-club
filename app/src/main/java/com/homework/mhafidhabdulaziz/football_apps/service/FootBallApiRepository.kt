package com.homework.mhafidhabdulaziz.football_apps.service

import java.net.URL

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class FootBallApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}