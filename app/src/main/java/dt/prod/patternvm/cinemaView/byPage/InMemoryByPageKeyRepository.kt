package dt.prod.patternvm.cinemaView.byPage

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dt.prod.patternvm.cinemaView.data.CinemaViewRepositoryImpl
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import dt.prod.patternvm.cinemaView.domain.PagingRepository

class InMemoryByPageKeyRepository(private val redditApi: CinemaViewApi) : PagingRepository {
    override fun postsOfSubreddit(pageSize: Int) = Pager(
        PagingConfig(pageSize)
    ) {
        CinemaViewRepositoryImpl(
            createEventApi = redditApi
        )
    }.flow
}
