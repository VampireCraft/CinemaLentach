package dt.prod.patternvm.cinemaView.domain

import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel
import dt.prod.patternvm.core.model.Event

interface CinemaViewRepository {
    suspend fun createEvent(offset: String): Event<List<CinemaOffsetModel>>
}