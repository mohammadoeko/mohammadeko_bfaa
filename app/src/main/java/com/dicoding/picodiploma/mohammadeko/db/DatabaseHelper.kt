package com.dicoding.picodiploma.mohammadeko.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbMohEko"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseMohEko.FavColumns.USERNAME} TEXT PRIMARY KEY  NOT NULL," +
                " ${DatabaseMohEko.FavColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseMohEko.FavColumns.AVATAR} TEXT NOT NULL," +
                " ${DatabaseMohEko.FavColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseMohEko.FavColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseMohEko.FavColumns.REPOSITORY} INTEGER NOT NULL," +
                " ${DatabaseMohEko.FavColumns.FOLLOWERS} INTEGER NOT NULL," +
                " ${DatabaseMohEko.FavColumns.FOLLOWING} INTEGER NOT NULL," +
                " ${DatabaseMohEko.FavColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}