package dt.prod.patternvm.cinemaView.domain

import androidx.paging.PagingData
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel
import kotlinx.coroutines.flow.Flow

interface PagingRepository {
    fun cinemaOfList(pageSize: Int): Flow<PagingData<CinemaOffsetModel>>
}