package com.example.george.movieguide.application

import android.app.Application
import com.example.george.movieguide.di.*
import org.koin.android.ext.android.startKoin

class MovieGuide : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
                appModule,
                apiModule,
                moviesModule,
                apiModuleDetails,
                moviesModuleDetails))

    }
}