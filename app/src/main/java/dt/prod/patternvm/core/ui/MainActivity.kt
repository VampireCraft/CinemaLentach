package dt.prod.patternvm.core.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import dt.prod.patternvm.cinemaView.ui.CinemasAdapter
import dt.prod.patternvm.databinding.ActivityMainBinding
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
        //initSwipeToRefresh()
        viewModel.showSubreddit("androiddev")
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = CinemasAdapter(glide)
        binding.rvCinemaList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PostsLoadStateAdapter(adapter),
            footer = PostsLoadStateAdapter(adapter)
        )

//        lifecycleScope.launchWhenCreated {
//            adapter.loadStateFlow.collect { loadStates ->
//                binding.srlRefreshList.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
//            }
//        }

        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Use a state-machine to track LoadStates such that we only transition to
                // NotLoading from a RemoteMediator load if it was also presented to UI.
                .asMergedLoadStates()
                // Only emit when REFRESH changes, as we only want to react on loads replacing the
                // list.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                // Scroll to top is synchronous with UI updates, even if remote load was triggered.
                .collect { binding.rvCinemaList.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        //binding.srlRefreshList.setOnRefreshListener { adapter.refresh() }
    }
}