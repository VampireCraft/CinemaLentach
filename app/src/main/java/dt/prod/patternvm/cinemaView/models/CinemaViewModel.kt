package dt.prod.patternvm.cinemaView.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dt.prod.patternvm.cinemaView.domain.PagingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

class CinemaViewModel(
    private val repository: PagingRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val KEY_LIST = "list"
        const val DEFAULT_SUBREDDIT = "androiddev"
    }

    init {
        if (!savedStateHandle.contains(KEY_LIST)) {
            savedStateHandle.set(KEY_LIST, DEFAULT_SUBREDDIT)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val posts = savedStateHandle.getLiveData<String>(KEY_LIST)
        .asFlow()
        .flatMapLatest { repository.cinemaOfList( 20) }
        .cachedIn(viewModelScope)

    fun showList(keyList: String) {
        if (!shouldShowSubreddit(keyList)) return
        savedStateHandle.set(KEY_LIST, keyList)
    }

    private fun shouldShowSubreddit(subreddit: String): Boolean {
        return savedStateHandle.get<String>(KEY_LIST) != subreddit
    }
}
