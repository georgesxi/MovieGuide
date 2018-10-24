package com.example.george.movieguide.di

import com.example.george.movieguide.views.moviedetails.MovieDetailsActivity
import com.example.george.movieguide.views.moviedetails.MovieDetailsViewModel
import com.example.george.movieguide.repository.TMDBApiMovieDetails
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val apiModuleDetails: Module = module(override = true) {
    factory(override = true) { TMDBApiMovieDetails.create() }
}
val moviesModuleDetails: Module = module(override = true) {
    single { MovieDetailsActivity() }
    factory { MovieDetailsViewModel(getProperty("id"),TMDBApiMovieDetails.create()) }
}