package com.example.george.movieguide.repository


import com.example.george.movieguide.model.*


import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiMovieDetails {

    @GET("movie/{id}")
    fun getDetails(@Path("id") id: Int,
                   @Query("language") language: String,
                   @Query("append_to_response") credits: String,
                   @Query("api_key") api_key: String)
            : Observable<Details>

    //Similar
    @GET("movie/{id}/similar")
    fun getSimilarMovies(@Path("id") id: Int,
                         @Query("language") language: String,
                         @Query("api_key") api_key: String)
            : Observable<Similar>


    //Reviews
    @GET("movie/{id}/reviews")
    fun getReviews(@Path("id") id: Int,
                   @Query("language") language: String,
                   @Query("api_key") api_key: String)
            : Observable<Reviews>


    companion object {
        fun create(): TMDBApiMovieDetails {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.themoviedb.org/3/")
                    .build()
            return retrofit.create(TMDBApiMovieDetails::class.java)
        }
    }
}
