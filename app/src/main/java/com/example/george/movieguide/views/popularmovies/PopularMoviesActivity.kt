package com.example.george.movieguide.views.popularmovies

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.george.movieguide.R
import com.example.george.movieguide.adapter.InfiniteScrollListener
import com.example.george.movieguide.adapter.MovieAdapterRecycle
import com.example.george.movieguide.adapter.MovieSearchAdapterRecycle

import com.example.george.movieguide.model.MoviesResults
import com.example.george.movieguide.model.Result
import com.example.george.movieguide.repository.TMDBApiMovieSearch
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

import org.koin.android.ext.android.inject

import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.no_network_layout.*
import org.koin.android.ext.android.setProperty
import java.util.concurrent.TimeUnit

class PopularMoviesActivity : AppCompatActivity() {

    private var mAndroidArrayList: MutableList<MoviesResults>? = null
    private var mSearchAndroidArrayList: MutableList<Result>? = null
    private var mAdapter: MovieAdapterRecycle? = null
    private var mSearchAdapter: MovieSearchAdapterRecycle? = null
    val linearLayout = LinearLayoutManager(this)
    private var disposable: Disposable? = null

    var context: Context = this

    val presenter: PopularMoviesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkConnectivity()) {

            setContentView(R.layout.activity_main)

            val sv: android.widget.SearchView = searchview
            initQuery(sv)


            presenter.getPopularMoviesRefreshObserver().onNext(Unit)

            disposable = presenter.getPopularMoviesRetrievedObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { handleResponse(it) }



            movie_list.addOnScrollListener(
                    InfiniteScrollListener({ loadMore() }, linearLayout))

            swipeContainer.setOnRefreshListener {

                if (checkConnectivity()) {
                    sv.setQuery("", false)
                    sv.clearFocus()
                    presenter.getPopularMoviesRefreshObserver().onNext(Unit)
                    disposable = presenter.getPopularMoviesRetrievedObservable()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { handleResponse(it) }

                    Log.i("TAG", "ADAPTER REFRESHED")

                    //Call refresh to renew recycler
                    Refresh()

                    swipeContainer.isRefreshing = false

                    movie_list.addOnScrollListener(
                            InfiniteScrollListener({ loadMore() }, linearLayout))
                } else {
                    setContentView(R.layout.no_network_layout)
                    refresh()
                }
            }
        } else {
            setContentView(R.layout.no_network_layout)
            refresh()
        }

    }

    fun refresh() {
        refresh.setOnClickListener {
            val intent = Intent(context, PopularMoviesActivity::class.java)
            startActivity(intent)
        }
    }


    fun checkConnectivity(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        return isConnected
    }


    fun initQuery(sv: android.widget.SearchView) {
        sv.queryHint = "Search here for movies..."
        sv.setIconifiedByDefault(false)


        disposable = Observable.create(ObservableOnSubscribe<String> { subscriber ->
            sv.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(searchLetter: String?): Boolean {
                    subscriber.onNext(searchLetter!!)

                    return false
                }

                override fun onQueryTextSubmit(searchWord: String?): Boolean {
                    subscriber.onNext(searchWord!!)
                    sv.clearFocus()

                    return true
                }
            })
        })
                .map { text -> text.toLowerCase().trim() }
                .debounce(200, TimeUnit.MILLISECONDS)
                // prevents consecutive duplicate requests
                .distinctUntilChanged()
                .filter { text -> text.isNotBlank() }
                .subscribe { text ->
                    setProperty("query", text)
                    var answer = TMDBApiMovieSearch.create().getSearchResults(text, "en-US", "3fa9058382669f72dcb18fb405b7a831", 1, false)
                    val newanswer = answer.map { it.results }
                    newanswer.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                handleSearchResponse(it)
                            }
                }

    }


    private fun handleResponse(androidList: MutableList<MoviesResults>?) {

        mAndroidArrayList = ArrayList(androidList!!)
        mAdapter = MovieAdapterRecycle(mAndroidArrayList!!, this, intent) { moviesResults ->

            // action when a recycler item is clicked
            Log.i("Listener", "ITEM WAS CLICKED " + moviesResults.title)
        }
        movie_list.adapter = mAdapter
        movie_list.layoutManager = linearLayout

        return
    }

    private fun handleSearchResponse(androidList: MutableList<Result>?) {

        mSearchAndroidArrayList = ArrayList(androidList!!)
        mSearchAdapter = MovieSearchAdapterRecycle(mSearchAndroidArrayList!!, this, intent) { moviesResults ->

            // action when a recycler item is clicked
            Log.i("Listener", "ITEM WAS CLICKED " + moviesResults.title)
        }

        movie_list.adapter = mSearchAdapter
        movie_list.layoutManager = linearLayout

        return
    }

    fun loadMore() {
        //TODO handle load more when it reaches total_pages
        // TODO handle disposable on activity termination

        presenter.getPopularMoviesRefreshObserver2().onNext(Unit)
        disposable = presenter.getPopularMoviesRetrievedObservable2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { mAndroidArrayList!!.addAll(it) }
                .take(1)
                .subscribe {
                    mAdapter!!.addAll(mAndroidArrayList!!)
                    Log.i("TAG", "${mAndroidArrayList} result found"
                    )
                }

        return
    }

    fun Refresh() {

        disposable!!.dispose()
        movie_list.Recycler().clear()
        movie_list.adapter = mAdapter
        movie_list.clearOnScrollListeners()
        presenter.ResetView()
    }
}