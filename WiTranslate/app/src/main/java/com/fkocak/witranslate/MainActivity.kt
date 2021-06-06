package com.fkocak.witranslate

import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fkocak.witranslate.base.activity.BaseActivity
import com.fkocak.witranslate.databinding.ActivityMainBinding
import com.fkocak.witranslate.deleteAfter.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {

    private val list = ArrayList<Movie>()
    private val listingVM by viewModels<ListingViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun initChangeFont() {
        title = "Trending Movies"
    }

    override fun initReq() {
        listingVM.fetchMovies()
    }

    override fun initVMListener() {
        prepareWithBaseVM(listingVM)

        listingVM.movieList.observe(this, { result ->
            moviesAdapter.updateData(result)
        })
    }

    override fun initChangeUI() {
        updateAdapter()
    }

    private fun updateAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvMovies.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvMovies.context,
            layoutManager.orientation
        )

        binding.rvMovies.addItemDecoration(dividerItemDecoration)
        moviesAdapter = MoviesAdapter(this, list)
        binding.rvMovies.adapter = moviesAdapter
    }

}