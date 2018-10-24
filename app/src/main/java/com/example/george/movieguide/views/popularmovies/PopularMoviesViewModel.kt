package com.example.george.movieguide.views.popularmovies

import com.example.george.movieguide.BuildConfig
import com.example.george.movieguide.model.MoviesResults
import com.example.george.movieguide.repository.TMDBApiService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PopularMoviesViewModel(val tmdbApiService: TMDBApiService) {

    private val refreshSubject: PublishSubject<Unit> = PublishSubject.create()
    private val refreshSubject2: PublishSubject<Unit> = PublishSubject.create()
    private val basicApiCall: ConnectableObservable<MutableList<MoviesResults>>
    private val basicApiCall2: ConnectableObservable<MutableList<MoviesResults>>
    private val compositeDisposable = CompositeDisposable()
    private val compositeDisposable2 = CompositeDisposable()
    private var page: Int = 1
    var currentPage: Int = 1

    init {
        basicApiCall = refreshSubject

                .flatMap {
                    return@flatMap tmdbApiService.getPopular(page, "en-US", BuildConfig.apiKey)
                            .map { it.results }
                            .subscribeOn(Schedulers.io())
                }
                .publish()

        compositeDisposable.add(basicApiCall.connect())
        basicApiCall2 = refreshSubject2
                .map { currentPage += 1 }
                .flatMap {
                    return@flatMap tmdbApiService.getPopular2(currentPage, "en-US", BuildConfig.apiKey)
                            .map { it.results }
                            .subscribeOn(Schedulers.io())
                }
                .publish()

        compositeDisposable2.add(basicApiCall2.connect())
    }

    fun getPopularMoviesRefreshObserver(): Observer<Unit> {
        return refreshSubject
    }

    fun getPopularMoviesRetrievedObservable(): Observable<MutableList<MoviesResults>> {
        return basicApiCall
    }

    fun getPopularMoviesRefreshObserver2(): Observer<Unit> {
        return refreshSubject2
    }

    fun getPopularMoviesRetrievedObservable2(): Observable<MutableList<MoviesResults>> {
        return basicApiCall2
    }

    fun ResetView() {
        currentPage = 1
    }
}