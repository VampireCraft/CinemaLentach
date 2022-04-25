package dt.prod.patternvm.cinemaView.domain

import dt.prod.patternvm.BuildConfig
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel
import retrofit2.http.*
import dt.prod.patternvm.core.model.ResponseWrapper

interface CinemaViewApi {

    @GET("/svc/movies/v2/reviews/all.json")
    suspend fun createEvent(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY,
        @Query("offset") offset: String
    ): ResponseWrapper<List<CinemaOffsetModel>>


}