package dt.prod.patternvm.cinemaView

import android.content.Context
import androidx.annotation.VisibleForTesting
import dt.prod.patternvm.cinemaView.byPage.InMemoryByPageKeyRepository
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import dt.prod.patternvm.cinemaView.domain.PagingRepository

interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }

        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(): PagingRepository

    fun getRedditApi(): CinemaViewApi
}

open class DefaultServiceLocator() : ServiceLocator {

    private val api by lazy {
        CinemaViewApi.create()
    }

    override fun getRepository(): PagingRepository {
            return InMemoryByPageKeyRepository(redditApi = getRedditApi())
    }

    override fun getRedditApi(): CinemaViewApi = api
}