package com.example.george.movieguide.views.moviedetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.george.movieguide.R
import com.example.george.movieguide.utils.GlideApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.setProperty
import com.example.george.movieguide.adapter.SimilarMoviesAdapterRecycle
import com.example.george.movieguide.model.*
import com.example.george.movieguide.utils.MoviePreference
import com.example.george.movieguide.utils.toast
import kotlin.collections.ArrayList

class MovieDetailsActivity : AppCompatActivity() {

    private var mSimilarMoviesArrayList: List<SimilarResults> = emptyList()
    private var mSimilarMoviesAdapter: SimilarMoviesAdapterRecycle? = null
    val mHorizontalLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    val presenter1: MovieDetailsViewModel by inject()
    private var disposable: Disposable? = null
    val moviePreference = MoviePreference(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        var movieId = intent.getStringExtra("movieId")
        setProperty("id", movieId)

        presenter1.getPopularMoviesRefreshObserver().onNext(Unit)
        disposable = presenter1.getPopularMoviesRetrievedObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleResponse(it) }

        presenter1.getReviewsRefreshObserver().onNext(Unit)
        disposable = presenter1.getReviewsRetrievedObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleReviewResponse(it) }

        presenter1.getSimilarMoviesRefreshObserver().onNext(Unit)
        disposable = presenter1.getSimilarMoviesRetrievedObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleSimilarMoviesResponse(it) }


    }

    private fun handleResponse(androidList: Details) {

        overview_details.text = androidList.overview
        movie_title_details.text = androidList.title
        movie_rating_details.text = androidList.voteAverage.toString()
        movie_date_details.text = androidList.releaseDate

        var movieGenre: MutableList<String> = mutableListOf()
        val crew = androidList.credits.crew
        val director: MutableList<String> = mutableListOf()
        val cast = androidList.credits.cast
        val starCast: MutableList<String> = mutableListOf()

        button_favorite.setOnClickListener {

            if (button_favorite.isChecked) {

                button_favorite.isChecked
                moviePreference.addDetailFavorite(this, androidList)
                this.toast(androidList.title + " movie was added to favorites")
            } else {
                button_favorite.isChecked = false
                moviePreference.removeDetailFavorite(this, androidList)
                this.toast(androidList.title + " movie was removed from favorites")
            }
        }

        button_favorite.isChecked = moviePreference.checkDetailFavorites(this, androidList)

        for (i in androidList.genres) {

            movieGenre.add(i.name)
        }
        movie_genre.text = movieGenre.toString().replace("[", "").replace("]", "")

        for (i in crew) {
            if (i.job == "Director") {
                director.add(i.name)
            }
        }
        director_details.text = director.toString().replace("[", "").replace("]", "")

        for (i in cast.take(10)) {
            starCast.add(i.name)
        }
        cast_details.text = starCast.toString().replace("[", "").replace("]", "")

        GlideApp.with(applicationContext).load("http://image.tmdb.org/t/p/w500//" + androidList.backdropPath)
                .transition(DrawableTransitionOptions.withCrossFade().crossFade())
                .into(movie_poster_details)


        //Support Action Bar Options after image loads
        setSupportActionBar(findViewById(R.id.details_back_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        return
    }

    private fun handleReviewResponse(reviewList: Reviews) {
        val topReviewsAuthor: MutableList<String> = mutableListOf()
        val topReviewsContent: MutableList<String> = mutableListOf()

        for (i in reviewList.results.take(2)) {
            topReviewsAuthor.add(i.author)
            topReviewsContent.add(i.content)
        }
        if (topReviewsAuthor.isEmpty() || topReviewsContent.isEmpty()) {
            review_author.text = getString(R.string.No_reviews)
            return
        } else if (topReviewsAuthor.size == 1) {
            review_author.text = topReviewsAuthor[0]
            review_details.text = topReviewsContent[0]

        } else if (topReviewsAuthor.size == 2) {
            review_author.text = topReviewsAuthor[0]
            review_details.text = topReviewsContent[0]

            review_author2.text = topReviewsAuthor[1]
            review_details2.text = topReviewsContent[1]
        }

    }

    private fun handleSimilarMoviesResponse(similarMoviesList: List<SimilarResults>) {

        mSimilarMoviesArrayList = ArrayList(similarMoviesList)

        mSimilarMoviesAdapter = SimilarMoviesAdapterRecycle(mSimilarMoviesArrayList as ArrayList<SimilarResults>, this, intent) { moviesResults ->
            // action when a recycler item is clicked
            Log.i("Listener", "ITEM WAS CLICKED " + moviesResults.title)
        }
        similar_movie_list.adapter = mSimilarMoviesAdapter  //needed to display results
        similar_movie_list.layoutManager = mHorizontalLayout
        return
    }
}