package dt.prod.patternvm.cinemaView.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import dt.prod.patternvm.cinemaView.GlideApp
import dt.prod.patternvm.cinemaView.ServiceLocator
import dt.prod.patternvm.cinemaView.models.CinemaViewModel
import dt.prod.patternvm.cinemaView.paging.asMergedLoadStates
import dt.prod.patternvm.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: CinemaViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repo = ServiceLocator.instance(this@MainActivity).getRepository()
                @Suppress("UNCHECKED_CAST")
                return CinemaViewModel(repo, handle) as T
            }
        }
    }

    private lateinit var adapter: CinemasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initList()
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = CinemasAdapter(glide)
        binding.rvCinemaList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PostsLoadStateAdapter(adapter),
            footer = PostsLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .asMergedLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvCinemaList.scrollToPosition(0) }
        }

    }

    private fun initList(){
        viewModel.showList("list")
        lifecycleScope.launchWhenStarted {
            closeIntro()
        }
    }

    private suspend fun closeIntro(){
        delay(2000)
        binding.ivIntro.visibility = GONE
        binding.tvIntro.visibility = GONE
        delay(200)
        binding.rvCinemaList.visibility = VISIBLE
    }
}