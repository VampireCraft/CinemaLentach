package dt.prod.patternvm.cinemaView.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dt.prod.patternvm.cinemaView.data.CinemaViewRepositoryImpl
import dt.prod.patternvm.cinemaView.domain.CinemaViewRepository
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CinemaViewModule {

    @Singleton
    @Provides
    fun provideCinemaViewRepository(
        cinemaViewApi: CinemaViewApi
    ): CinemaViewRepository {
        return CinemaViewRepositoryImpl(cinemaViewApi)
    }
}