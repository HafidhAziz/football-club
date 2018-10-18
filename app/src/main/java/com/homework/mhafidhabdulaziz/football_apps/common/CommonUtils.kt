package com.homework.mhafidhabdulaziz.football_apps.common

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
object CommonUtils {

    fun getFormattedDate(date: Date): String {
        return SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.getDefault()).format(date)
    }

    fun getSeparator(string: String): String {
        return string.replace("; ", System.getProperty("line.separator"))
    }

    fun getSeparatorNoSpace(string: String): String {
        return string.replace(";", System.getProperty("line.separator"))
    }
}