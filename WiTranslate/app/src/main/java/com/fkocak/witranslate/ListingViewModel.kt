package com.fkocak.witranslate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fkocak.witranslate.base.vm.BaseVM
import com.fkocak.witranslate.deleteAfter.MovieRepository
import com.fkocak.witranslate.deleteAfter.model.Movie
import com.fkocak.witranslate.deleteAfter.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for ListingActivity
 */
@HiltViewModel
class ListingViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseVM() {

    //private val _movieList = MutableLiveData<Result<TrendingMovieResponse>>()
    val movieList = MutableLiveData<List<Movie>>()

    fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchTrendingMovies().collect {
                when (it?.status) {
                    Result.Status.SUCCESS -> {
                        it.data?.results?.let { list ->
                            movieList.value = list
                        }
                        loadingHUD.value = false
                    }

                    Result.Status.ERROR -> {
                        it.message?.let {
                            checkForErrWarMes.value = it
                        }
                        loadingHUD.value = false
                    }

                    Result.Status.LOADING -> {
                        loadingHUD.value = true
                    }
                }

            }
        }
    }
}