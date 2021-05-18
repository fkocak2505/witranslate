package com.fkocak.witranslate

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fkocak.witranslate.base.BaseActivity
import com.fkocak.witranslate.databinding.ActivityMainBinding
import com.fkocak.witranslate.deleteAfter.model.Movie
import com.fkocak.witranslate.deleteAfter.model.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {

    private val list = ArrayList<Movie>()
    private val viewModel by viewModels<ListingViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun initVM() {
        title = "Trending Movies"
        val layoutManager = LinearLayoutManager(this)
        binding.rvMovies.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvMovies.context,
            layoutManager.orientation
        )

        binding.rvMovies.addItemDecoration(dividerItemDecoration)
        moviesAdapter = MoviesAdapter(this, list)
        binding.rvMovies.adapter = moviesAdapter

        viewModel.movieList.observe(this, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        moviesAdapter.updateData(list)
                    }
                    binding.loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                    }
                    binding.loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }

        })
    }

    override fun initChangeFont() {

    }

    override fun initReq() {

    }

    override fun initVMListener() {

    }

    override fun onCreateMethod() {

    }

    private fun showError(msg: String) {
        Snackbar.make(binding.vParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

}