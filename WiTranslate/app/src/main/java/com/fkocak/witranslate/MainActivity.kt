package com.fkocak.witranslate

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fkocak.witranslate.base.BaseActivity
import com.fkocak.witranslate.databinding.ActivityMainBinding
import com.fkocak.witranslate.deleteAfter.CharactersAdapter
import com.fkocak.witranslate.deleteAfter.CharactersViewModel
import com.fkocak.witranslate.deleteAfter.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it)}),
    CharactersAdapter.CharacterItemListener {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var adapter: CharactersAdapter

    override fun initVM() {
        setupRecyclerView()
        setupObservers()
    }

    override fun initChangeFont() {

    }

    override fun initReq() {

    }

    override fun initVMListener() {

    }

    override fun onCreateMethod() {

    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedCharacter(characterId: Int) {
        Toast.makeText(this@MainActivity, characterId.toString(), Toast.LENGTH_SHORT).show()
    }

}