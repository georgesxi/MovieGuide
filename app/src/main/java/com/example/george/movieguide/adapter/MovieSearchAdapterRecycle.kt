package com.example.george.movieguide.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.george.movieguide.R
import com.example.george.movieguide.model.Result
import com.example.george.movieguide.utils.MoviePreference
import com.example.george.movieguide.utils.checkConnectivity
import com.example.george.movieguide.utils.toast
import com.example.george.movieguide.views.moviedetails.MovieDetailsActivity
import com.example.george.movieguide.views.popularmovies.PopularMoviesActivity
import kotlinx.android.synthetic.main.list_item.view.*

class MovieSearchAdapterRecycle(val searchMovies: MutableList<Result>,
                                val context: Context,
                                val intent: Intent,
                                val listener: (Result) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount() = searchMovies.size

    var mItems: MutableList<Result>? = null
    val moviePreference = MoviePreference(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return SearchMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieSearchAdapterRecycle.SearchMovieViewHolder).bind(searchMovies[position])
    }

    inner class SearchMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies1: Result) {

            itemView.movie_title.text = movies1.title
            Glide.with(itemView).load("http://image.tmdb.org/t/p/w500//" + movies1.backdropPath).into(itemView.movie_poster)
            itemView.movie_rating.text = movies1.voteAverage.toString()
            itemView.movie_date.text = movies1.releaseDate
            itemView.button_favorite.setOnClickListener {

                if (itemView.button_favorite.isChecked) {

                    itemView.button_favorite.isChecked
                    moviePreference.addSearchFavorite(context, movies1)
                    context.toast(movies1.title + " movie was added to favorites")

                } else {

                    itemView.button_favorite.isChecked = false
                    moviePreference.removeSearchFavorite(context, movies1)
                    context.toast(movies1.title + " movie was removed from favorites")
                }
            }

            itemView.button_favorite.isChecked = moviePreference.checkSearchFavorites(context, movies1)

            itemView.list_item.setOnClickListener {
                if (checkConnectivity(context)) {
                    val intent = Intent(context, MovieDetailsActivity::class.java)

                    // To pass any data to next activity
                    var moviesId = movies1.id.toString()
                    intent.putExtra("movieId", moviesId)
                    itemView.context.startActivity(intent)
                } else {
                    val intent = Intent(itemView.context, PopularMoviesActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }


    fun setItems(items: MutableList<Result>) {
        mItems = items
    }

    //Add a list of items
    fun addAll(list: MutableList<Result>) {
        mItems?.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(list: MutableList<Result>) {
        mItems?.clear()
        list.clear()
        notifyDataSetChanged()
    }
}