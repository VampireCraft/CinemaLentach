package dt.prod.patternvm.cinemaView.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dt.prod.patternvm.cinemaView.data.CinemaViewRepositoryImpl
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import dt.prod.patternvm.cinemaView.domain.PagingRepository

class PageKeyRepository(private val cinemaViewApi: CinemaViewApi) : PagingRepository {
    override fun cinemaOfList(pageSize: Int) = Pager(
        PagingConfig(pageSize)
    ) {
        CinemaViewRepositoryImpl(
            cinemaViewApi = cinemaViewApi
        )
    }.flow
}
