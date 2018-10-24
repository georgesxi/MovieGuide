package com.example.george.movieguide.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.george.movieguide.R
import com.example.george.movieguide.views.moviedetails.MovieDetailsActivity
import com.example.george.movieguide.model.MoviesResults
import kotlinx.android.synthetic.main.list_item.view.*
import com.example.george.movieguide.utils.toast
import com.example.george.movieguide.utils.MoviePreference
import com.example.george.movieguide.utils.checkConnectivity
import com.example.george.movieguide.views.popularmovies.PopularMoviesActivity

class MovieAdapterRecycle(

        val movies1: MutableList<MoviesResults>,
        val context: Context,
        val intent: Intent,
        val listener: (MoviesResults) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mItems: MutableList<MoviesResults>? = null
    val moviePreference = MoviePreference(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(movies1[position])
    }

    override fun getItemCount() = movies1.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies1: MoviesResults) {

            itemView.movie_title.text = movies1.title
            Glide.with(itemView).load("http://image.tmdb.org/t/p/w500//" + movies1.backdrop).into(itemView.movie_poster)
            itemView.movie_rating.text = movies1.vote_average.toString()
            itemView.movie_date.text = movies1.release_date
            itemView.button_favorite.setOnClickListener {

                if (itemView.button_favorite.isChecked) {
                    itemView.button_favorite.isChecked
                    moviePreference.addFavorite(context, movies1)
                    context.toast(movies1.title + " movie was added to favorites")

                } else {

                    itemView.button_favorite.isChecked = false
                    moviePreference.removeFavorite(context, movies1)
                    context.toast(movies1.title + " movie was removed from favorites")
                }
            }

            itemView.button_favorite.isChecked = moviePreference.checkFavorites(context, movies1)
            itemView.list_item.setOnClickListener {

                if (checkConnectivity(context)) {

                    val intent = Intent(itemView.context, MovieDetailsActivity::class.java)

                    // To pass any data to next activity
                    var moviesId = movies1.id
                    intent.putExtra("movieId", moviesId)
                    itemView.context.startActivity(intent)
                } else {
                    val intent = Intent(itemView.context, PopularMoviesActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    fun setItems(items: MutableList<MoviesResults>) {
        mItems = items
    }

    //Add a list of items
    fun addAll(list: MutableList<MoviesResults>) {
        mItems?.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(list: MutableList<MoviesResults>) {
        mItems?.clear()
        list.clear()
        notifyDataSetChanged()
    }
}