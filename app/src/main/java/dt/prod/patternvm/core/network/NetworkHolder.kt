package dt.prod.patternvm.core.network

import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

object NetworkHolder {
     const val baseurl = "https://api.nytimes.com/"

     var retrofitClient: Retrofit
     var httpClient: OkHttpClient

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 3

        httpClient = OkHttpClient().newBuilder()
            .followRedirects(true)
            .followSslRedirects(false)
            .addInterceptor(interceptor)
            .dispatcher(dispatcher)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        retrofitClient = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseurl)
            .client(httpClient)
            .build()
    }

    fun getCinemaViewApi(): CinemaViewApi {
        return retrofitClient.create(CinemaViewApi::class.java)
    }
}