package dt.prod.patternvm.cinemaView.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import dt.prod.patternvm.cinemaView.ui.CinemasAdapter
import dt.prod.patternvm.cinemaView.ui.NetworkStateItemViewHolder

class PostsLoadStateAdapter(
        private val adapter: CinemasAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { adapter.retry() }
    }
}