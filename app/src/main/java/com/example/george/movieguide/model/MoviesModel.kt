package com.example.george.movieguide.model

import com.google.gson.annotations.SerializedName

data class Results(@SerializedName("results") val results: MutableList<MoviesResults>)

data class MoviesResults( @SerializedName("vote_average") val vote_average: Double,
                         @SerializedName("title") val title: String,
                         @SerializedName("backdrop_path") val backdrop: String,
                         @SerializedName("release_date") val release_date: String,
                         @SerializedName("id") val id: String)


    data class Details(
            @SerializedName("adult") val adult: Boolean,
            @SerializedName("backdrop_path") val backdropPath: String,
            @SerializedName("belongs_to_collection") val belongsToCollection: Any,
            @SerializedName("budget") val budget: Int,
            @SerializedName("genres") val genres: List<Genre>,
            @SerializedName("homepage") val homepage: String,
            @SerializedName("id") val id: Int,
            @SerializedName("imdb_id") val imdbId: String,
            @SerializedName("original_language") val originalLanguage: String,
            @SerializedName("original_title") val originalTitle: String,
            @SerializedName("overview") val overview: String,
            @SerializedName("popularity") val popularity: Double,
            @SerializedName("poster_path") val posterPath: String,
            @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>,
            @SerializedName("production_countries") val productionCountries: List<ProductionCountry>,
            @SerializedName("release_date") val releaseDate: String,
            @SerializedName("revenue") val revenue: Int,
            @SerializedName("runtime") val runtime: Int,
            @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
            @SerializedName("status") val status: String,
            @SerializedName("tagline") val tagline: String,
            @SerializedName("title") val title: String,
            @SerializedName("video") val video: Boolean,
            @SerializedName("vote_average") val voteAverage: Double,
            @SerializedName("vote_count") val voteCount: Int,
            @SerializedName("credits") val credits: Credits
    )


    data class Genre(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String
    )

    data class ProductionCompany(
            @SerializedName("id") val id: Int,
            @SerializedName("logo_path") val logoPath: String,
            @SerializedName("name") val name: String,
            @SerializedName("origin_country") val originCountry: String
    )

    data class Credits(
            @SerializedName("cast") val cast: List<Cast>,
            @SerializedName("crew") val crew: List<Crew>
    )

    data class Crew(
            @SerializedName("credit_id") val creditId: String,
            @SerializedName("department") val department: String,
            @SerializedName("gender") val gender: Int,
            @SerializedName("id") val id: Int,
            @SerializedName("job") val job: String,
            @SerializedName("name") val name: String,
            @SerializedName("profile_path") val profilePath: Any
    )

    data class Cast(
            @SerializedName("cast_id") val castId: Int,
            @SerializedName("character") val character: String,
            @SerializedName("credit_id") val creditId: String,
            @SerializedName("gender") val gender: Int,
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String,
            @SerializedName("order") val order: Int,
            @SerializedName("profile_path") val profilePath: String
    )

    data class ProductionCountry(
            @SerializedName("iso_3166_1") val iso31661: String,
            @SerializedName("name") val name: String
    )

    data class SpokenLanguage(
            @SerializedName("iso_639_1") val iso6391: String,
            @SerializedName("name") val name: String
    )


data class Similar(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<SimilarResults>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class SimilarResults(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("popularity") val popularity: Double
)


data class Reviews(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: MutableList<ReviewResults>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class ReviewResults(
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)


data class Search(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: MutableList<Result>
)

data class Result(
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("backdrop_path") val backdropPath: Any,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
)
