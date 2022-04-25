package dt.prod.patternvm.cinemaView.ui

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import dt.prod.patternvm.R
import dt.prod.patternvm.cinemaView.GlideRequests
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel

class CinemaViewHolder(view: View, private val glide: GlideRequests)
    : RecyclerView.ViewHolder(view) {
    private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    private val tvDescrip: TextView = view.findViewById(R.id.tvDescrip)
    private val ivSrc : ImageView = view.findViewById(R.id.ivSrc)
    private var offset : CinemaOffsetModel? = null
    init {
        view.setOnClickListener {
            offset?.link?.url.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(offset: CinemaOffsetModel?) {
        this.offset = offset
        tvTitle.text = offset?.display_title ?: "loading"
        tvDescrip.text = offset?.summary_short ?: "unknown"
        if (offset?.media?.src?.startsWith("http") == true) {
            ivSrc.visibility = View.VISIBLE
            glide.load(offset.media!!.src)
                    .centerCrop()
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .into(ivSrc)
        } else {
            ivSrc.visibility = View.GONE
            glide.clear(ivSrc)
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): CinemaViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cinema_list, parent, false)
            return CinemaViewHolder(view, glide)
        }
    }

    fun updateScore(item: CinemaOffsetModel?) {
        offset = item
    }
}