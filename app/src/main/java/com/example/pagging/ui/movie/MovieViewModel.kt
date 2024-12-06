package com.example.pagging.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pagging.model.moviedetails.MovieDetails
import com.example.pagging.MovieInterface
import com.example.pagging.ui.details.MovieDetailsRepository
import com.example.pagging.utils.Result
import com.example.pagging.utils.Status
import com.example.pagging.utils.Events
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieInterface: MovieInterface,
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val query = MutableLiveData<String>("home alone")

    val list = query.switchMap {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePaging(query.value.toString(), movieInterface) }
        ).liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    private val _movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()
    val movieDetails: LiveData<Events<Result<MovieDetails>>> = _movieDetails


    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            _movieDetails.postValue(Events(Result(Status.LOADING, null, null)))
            _movieDetails.postValue(Events(repository.getMovieDetails(imdbID)))
        }
    }
}