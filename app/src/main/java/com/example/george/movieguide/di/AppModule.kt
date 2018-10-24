package com.example.george.movieguide.di

import android.content.res.Resources
import com.example.george.movieguide.repository.TMDBApiService
import com.example.george.movieguide.views.popularmovies.PopularMoviesActivity
import com.example.george.movieguide.views.popularmovies.PopularMoviesViewModel
import com.example.george.movieguide.application.MovieGuide
import com.example.george.movieguide.repository.TMDBApiMovieSearch
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModule = module(override = true) {
    single { createResources(get()) }
}

fun createResources(application: MovieGuide): Resources = application.resources

val apiModule: Module = module(override = true) {
    factory { TMDBApiService.create() }
    factory { TMDBApiMovieSearch.create() }
}

val moviesModule: Module = module(override = true) {

    single { PopularMoviesActivity() }
    single { PopularMoviesViewModel(TMDBApiService.create()) }
}





