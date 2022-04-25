package dt.prod.patternvm.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideCinemaViewApi(): CinemaViewApi {
        return NetworkHolder.getCinemaViewApi()
    }
}