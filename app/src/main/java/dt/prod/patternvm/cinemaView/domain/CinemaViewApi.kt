package dt.prod.patternvm.cinemaView.domain

import android.util.Log
import dt.prod.patternvm.BuildConfig
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel
import retrofit2.http.*
import dt.prod.patternvm.core.model.ResponseWrapper
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CinemaViewApi {

    @GET("/svc/movies/v2/reviews/all.json")
    suspend fun createEvent(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY,
        @Query("offset") offset: String
    ): ResponseWrapper<List<CinemaOffsetModel>>

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/"
        fun create(): CinemaViewApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL.toHttpUrlOrNull()!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CinemaViewApi::class.java)
        }
    }
}