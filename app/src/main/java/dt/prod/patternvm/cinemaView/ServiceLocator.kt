package dt.prod.patternvm.cinemaView

import android.content.Context
import dt.prod.patternvm.cinemaView.domain.PageKeyRepository
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
    }

    fun getRepository(): PagingRepository

    fun getRedditApi(): CinemaViewApi
}

open class DefaultServiceLocator() : ServiceLocator {

    private val api by lazy {
        CinemaViewApi.create()
    }

    override fun getRepository(): PagingRepository {
            return PageKeyRepository(cinemaViewApi = getRedditApi())
    }

    override fun getRedditApi(): CinemaViewApi = api
}