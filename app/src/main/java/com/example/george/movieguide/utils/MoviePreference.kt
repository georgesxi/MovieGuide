package com.example.george.movieguide.utils

import android.content.Context
import android.util.Log
import com.example.george.movieguide.model.Details
import com.example.george.movieguide.model.MoviesResults
import com.example.george.movieguide.model.Result


class MoviePreference(context: Context) {
    val PREFS_NAME: String = "MovieGuide"

    fun addFavorite(context: Context, moviesFavorite: MoviesResults) {

        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean(moviesFavorite.id, true).apply()
    }

    fun addSearchFavorite(context: Context, moviesFavorite: Result) {

        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean(moviesFavorite.id.toString(), true).apply()
    }

    fun addDetailFavorite(context: Context, moviesFavorite: Details) {

        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean(moviesFavorite.id.toString(), true).apply()
    }

    fun removeFavorite(context: Context, moviesFavorite: MoviesResults) {

        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean(moviesFavorite.id, false).apply()
    }

    fun removeSearchFavorite(context: Context, moviesFavorite: Result) {

        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean(moviesFavorite.id.toString(), false).apply()
    }

    fun removeDetailFavorite(context: Context, moviesFavorite: Details) {

        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean(moviesFavorite.id.toString(), false).apply()
    }

    fun checkFavorites(context: Context, currentMovie: MoviesResults): Boolean {
        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val check: Boolean

        val favorite1 = preference.getBoolean(currentMovie.id.toString(), false)

        Log.e("ALL FAVORITES TAG", " " + favorite1 + " ")

        if (favorite1) {
            Log.e("contains FAVORITES TAG", " " + favorite1)
            check = true
        } else {
            Log.e("not contains FAVOR TAG", " " + favorite1)
            check = false
        }
        return check
    }

    fun checkDetailFavorites(context: Context, currentMovie: Details): Boolean {
        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val check: Boolean

        val favorite1 = preference.getBoolean(currentMovie.id.toString(), false)

        if (favorite1) {
            check = true
        } else {
            check = false
        }
        return check
    }

    fun checkSearchFavorites(context: Context, currentMovie: Result): Boolean {
        val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val check: Boolean

        val favorite1 = preference.getBoolean(currentMovie.id.toString(), false)

        if (favorite1) {
            check = true
        } else {
            check = false
        }
        return check
    }
}