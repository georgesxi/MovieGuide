package com.example.george.movieguide.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.george.movieguide.R
import com.example.george.movieguide.model.SimilarResults

import kotlinx.android.synthetic.main.similar_movies_list_item.view.*


class SimilarMoviesAdapterRecycle(

        val similarMovies: MutableList<SimilarResults>,
        val context: Context,
        val intent: Intent,
        val listener: (SimilarResults) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.similar_movies_list_item, parent, false)
        return SimilarMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SimilarMovieViewHolder).bind(similarMovies[position])
    }

    override fun getItemCount() = similarMovies.size

    inner class SimilarMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(similarMovies: SimilarResults) {
            Glide.with(itemView).load("http://image.tmdb.org/t/p/w500//" + similarMovies.posterPath).into(itemView.similar_movie_poster)
        }

    }
}