package com.fkocak.witranslate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fkocak.witranslate.deleteAfter.MovieRepository
import com.fkocak.witranslate.deleteAfter.model.Result
import com.fkocak.witranslate.deleteAfter.model.TrendingMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for ListingActivity
 */
@HiltViewModel
class ListingViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _movieList = MutableLiveData<Result<TrendingMovieResponse>>()
    val movieList = _movieList

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchTrendingMovies().collect {
                _movieList.value = it
            }
        }
    }
}