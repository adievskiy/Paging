package com.example.pagging.ui.details

import com.example.pagging.model.moviedetails.MovieDetails
import com.example.pagging.MovieInterface
import com.example.pagging.utils.Constants
import com.example.pagging.utils.Result
import com.example.pagging.utils.Status

class MovieDetailsRepository(private val movieInterface: MovieInterface) {

    suspend fun getMovieDetails(imdbID: String): Result<MovieDetails> {
        return try {
            val response = movieInterface.getMoviesDetails(imdbID, Constants.API_KEY)

            if (response.isSuccessful) {
                return Result(Status.SUCCESS,response.body(), null)
            } else {
                Result(Status.ERROR,null, null)
            }

        } catch (e: Exception) {
            Result(Status.ERROR,null, null)
        }
    }
}