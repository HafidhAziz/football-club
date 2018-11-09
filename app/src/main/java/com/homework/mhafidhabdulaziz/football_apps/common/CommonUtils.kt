package com.homework.mhafidhabdulaziz.football_apps.common

import android.content.Context
import android.widget.ArrayAdapter
import com.homework.mhafidhabdulaziz.football_apps.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by M Hafidh Abdul Aziz on 10/16/2018.
 */
object CommonUtils {

    fun getFormattedDate(date: Date): String {
        return SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.getDefault()).format(date)
    }

    fun getFormattedTime(time: String): String {
        val inputFormat = SimpleDateFormat("HH:mm:ssZ", Locale("id", "ID"))
        val outputFormat = SimpleDateFormat("h:mm a", Locale("id", "ID"))
        val date = inputFormat.parse(time)
        return outputFormat.format(date)
    }

    fun getSeparator(string: String): String {
        return string.replace("; ", System.getProperty("line.separator"))
    }

    fun getSeparatorNoSpace(string: String): String {
        return string.replace(";", System.getProperty("line.separator"))
    }

    fun getLeagueSpinner(context: Context): ArrayAdapter<String> {
        val spinnerItems = context.resources.getStringArray(R.array.league)
        return ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinnerItems)
    }

    fun getSelectedLeague(context: Context, leagueName: String): String {
        when (leagueName) {
            context.resources.getString(R.string.title_epl) -> return "4328"
            context.resources.getString(R.string.title_bundesliga) -> return "4331"
            context.resources.getString(R.string.title_seriea) -> return "4332"
            context.resources.getString(R.string.title_ligue1) -> return "4334"
            context.resources.getString(R.string.title_laliga) -> return "4335"
            context.resources.getString(R.string.title_eredivisi) -> return "4337"
            else -> Constants.DEFAULT_LEAGUE_MATCH_ID
        }
        return Constants.DEFAULT_LEAGUE_MATCH_ID
    }
}