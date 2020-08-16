package com.dicoding.picodiploma.mohammadeko.helper

import android.database.Cursor
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko
import com.dicoding.picodiploma.mohammadeko.model.Favorite
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.AVATAR))
                val company = getString(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.LOCATION))
                val repository = getInt(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.REPOSITORY))
                val followers = getInt(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.FOLLOWING))
                val favorite =
                    getString(getColumnIndexOrThrow(DatabaseMohEko.FavColumns.FAVORITE))
                favList.add(
                    Favorite(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        favorite
                    )
                )
            }
        }
        return favList
    }
}