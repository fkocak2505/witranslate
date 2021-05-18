package com.fkocak.witranslate.deleteAfter.network.service

import com.fkocak.witranslate.deleteAfter.model.MovieDesc
import com.fkocak.witranslate.deleteAfter.model.TrendingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
interface MovieService {

    @GET("/3/trending/movie/week")
    suspend fun getPopularMovies() : Response<TrendingMovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int) : Response<MovieDesc>
}