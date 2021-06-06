package com.fkocak.witranslate.deleteAfter

import com.fkocak.witranslate.deleteAfter.data.local.MovieDao
import com.fkocak.witranslate.deleteAfter.data.remote.MovieRemoteDataSource
import com.fkocak.witranslate.deleteAfter.model.Movie
import com.fkocak.witranslate.deleteAfter.model.MovieDesc
import com.fkocak.witranslate.deleteAfter.model.TrendingMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.fkocak.witranslate.deleteAfter.model.Result
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDao: MovieDao
) {

    suspend fun fetchTrendingMovies(): Flow<Result<TrendingMovieResponse>?> {
        return flow {
            fetchTrendingMoviesCached()?.let {
                emit(Result.success(TrendingMovieResponse(it)))
            } ?: run {
                emit(Result.loading())
                val result = movieRemoteDataSource.fetchTrendingMovies()

                if (result.status == Result.Status.SUCCESS) {
                    result.data?.results?.let {
                        movieDao.deleteAll(it)
                        movieDao.insertAll(it)
                    }
                }
                emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchTrendingMoviesCached(): List<Movie>? {
//        movieDao.getAll()?.let {
//            return if (it.isEmpty())
//                null
//            else
//                it
//        } ?: run {
//            return null
//        }
        return null
    }

    suspend fun fetchMovie(id: Int): Flow<Result<MovieDesc>> {
        return flow {
            emit(Result.loading())
            emit(movieRemoteDataSource.fetchMovie(id))
        }.flowOn(Dispatchers.IO)
    }
}