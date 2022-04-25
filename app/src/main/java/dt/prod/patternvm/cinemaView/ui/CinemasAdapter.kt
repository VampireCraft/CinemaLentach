package dt.prod.patternvm.cinemaView.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dt.prod.patternvm.cinemaView.GlideRequests
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel

class CinemasAdapter(private val glide: GlideRequests)
    : PagingDataAdapter<CinemaOffsetModel, CinemaViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
            holder: CinemaViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        return CinemaViewHolder.create(parent, glide)
    }

    companion object {

        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CinemaOffsetModel>() {
            override fun areContentsTheSame(oldItem: CinemaOffsetModel, newItem: CinemaOffsetModel): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: CinemaOffsetModel, newItem: CinemaOffsetModel): Boolean =
                    oldItem.display_title == newItem.display_title
        }
    }
}
