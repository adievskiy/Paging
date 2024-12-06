package com.example.pagging.ui.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagging.MovieInterface
import com.example.pagging.model.movie.Movie
import com.example.pagging.utils.Constants

class MoviePaging(val s: String, val movieInterface: MovieInterface) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val data = movieInterface.getAllMovies(s, page, Constants.API_KEY)

            LoadResult.Page(
                data = data.body()?.Search!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.Search!!.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}