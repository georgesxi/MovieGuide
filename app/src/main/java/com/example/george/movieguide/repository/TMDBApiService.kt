package com.example.george.movieguide.repository


import com.example.george.movieguide.model.Results

import io.reactivex.Observable

import retrofit2.Retrofit

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int,
                   @Query("language") language: String,
                   @Query("api_key") api_key: String)
            : Observable<Results>

    @GET("movie/popular")
    fun getPopular2(@Query("page") page: Int,
                    @Query("language") language: String,
                    @Query("api_key") api_key: String)
            : Observable<Results>

    companion object {
        fun create(): TMDBApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.themoviedb.org/3/")
                    .build()
            return retrofit.create(TMDBApiService::class.java)
        }

    }
}