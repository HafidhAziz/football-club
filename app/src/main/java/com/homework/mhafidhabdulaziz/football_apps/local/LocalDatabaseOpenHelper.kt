package com.homework.mhafidhabdulaziz.football_apps.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by M Hafidh Abdul Aziz on 10/17/2018.
 */
class LocalDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1) {

    companion object {
        private var instance: LocalDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabaseOpenHelper {
            if (instance == null) {
                instance = LocalDatabaseOpenHelper(context.applicationContext)
            }
            return instance as LocalDatabaseOpenHelper
        }
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.createTable("TABLE_FAVORITE", true,
                "ID" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "EVENT_ID" to TEXT + UNIQUE,
                "HOME_TEAM" to TEXT,
                "AWAY_TEAM" to TEXT)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, version: Int, newVersion: Int) {
        sqLiteDatabase.dropTable("TABLE_FAVORITE", true)
    }
}

val Context.database: LocalDatabaseOpenHelper
    get() = LocalDatabaseOpenHelper.getInstance(applicationContext)