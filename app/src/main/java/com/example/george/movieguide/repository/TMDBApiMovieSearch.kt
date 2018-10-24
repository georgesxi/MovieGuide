package com.example.george.movieguide.repository


import com.example.george.movieguide.model.Search
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiMovieSearch {

    @GET("search/movie/")
    fun getSearchResults(
            @Query("query") query: String,
            @Query("language") language: String,
            @Query("api_key") api_key: String,
            @Query("page") page: Int,
            @Query("include_adult") include_adult: Boolean)
            : Observable<Search>


    companion object {
        fun create(): TMDBApiMovieSearch {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.themoviedb.org/3/")
                    .build()
            return retrofit.create(TMDBApiMovieSearch::class.java)
        }
    }
}