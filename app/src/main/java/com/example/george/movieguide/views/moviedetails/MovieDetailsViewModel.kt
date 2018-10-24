package com.example.george.movieguide.views.moviedetails

import com.example.george.movieguide.BuildConfig.apiKey
import com.example.george.movieguide.model.*


import com.example.george.movieguide.repository.TMDBApiMovieDetails

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class MovieDetailsViewModel(val id: Int, val tmdbApiMovieDetails: TMDBApiMovieDetails) {

    private val refreshSubject: PublishSubject<Unit> = PublishSubject.create()
    private val basicApiCall: ConnectableObservable<Details>
    private val compositeDisposable = CompositeDisposable()

    private val reviewRefreshSubject: PublishSubject<Unit> = PublishSubject.create()
    private val reviewsApiCall: ConnectableObservable<Reviews>
    private val reviewsCompositeDisposable = CompositeDisposable()

    private val similarMoviesRefreshSubject: PublishSubject<Unit> = PublishSubject.create()
    private val similarMoviesApiCall: ConnectableObservable<List<SimilarResults>>
    private val similarMoviesCompositeDisposable = CompositeDisposable()

    private var page: Int = 1
    var currentPage: Int = 1

    init {

        basicApiCall = refreshSubject
                .flatMap {
                    return@flatMap tmdbApiMovieDetails.getDetails(id, "en-US", "credits", apiKey)
                            .map { it }
                            .subscribeOn(Schedulers.io())
                }
                .publish()

        compositeDisposable.add(basicApiCall.connect())

      //Reviews fetch request
        reviewsApiCall = reviewRefreshSubject
                .flatMap {
                    return@flatMap tmdbApiMovieDetails.getReviews(id, "en-US", apiKey)
                            .map { it }

                            .subscribeOn(Schedulers.io())
                }
                .publish()

        reviewsCompositeDisposable.add(reviewsApiCall.connect())

        //Similar movies fetch request
        similarMoviesApiCall = similarMoviesRefreshSubject
                .flatMap {
                    return@flatMap tmdbApiMovieDetails.getSimilarMovies(id, "en-US", apiKey)
                            .map { it.results }

                            .subscribeOn(Schedulers.io())
                }
                .publish()

        similarMoviesCompositeDisposable.add(similarMoviesApiCall.connect())


    }

    fun getPopularMoviesRefreshObserver(): Observer<Unit> {
        return refreshSubject
    }

    fun getPopularMoviesRetrievedObservable(): Observable<Details> {
        return basicApiCall
    }

    fun getReviewsRefreshObserver(): Observer<Unit> {
        return reviewRefreshSubject
    }

    fun getReviewsRetrievedObservable(): Observable<Reviews> {
        return reviewsApiCall
    }

    fun getSimilarMoviesRefreshObserver(): Observer<Unit> {
        return similarMoviesRefreshSubject
    }

    fun getSimilarMoviesRetrievedObservable(): Observable<List<SimilarResults>> {
        return similarMoviesApiCall
    }

    fun ResetView() {
        currentPage = 1
    }

}