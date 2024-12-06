package com.example.pagging

import com.example.pagging.model.movie.MovieResponse
import com.example.pagging.model.moviedetails.MovieDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {
    @GET("/")
    suspend fun getAllMovies(
        @Query("s") s: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMoviesDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apikey: String
    ): Response<MovieDetails>
}