package dt.prod.patternvm.cinemaView.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel
import retrofit2.HttpException
import java.io.IOException

class CinemaViewRepositoryImpl(private val createEventApi: CinemaViewApi) : PagingSource<String, CinemaOffsetModel>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CinemaOffsetModel> {
        return try {
            val items = createEventApi.createEvent(
                    offset = params.key ?: "1"
                ).data

            LoadResult.Page(
                data = items!!.map { it },
                prevKey = params.key,
                nextKey = if (params.key.isNullOrEmpty()) "21" else (params.key!!.toInt() + 20).toString()
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, CinemaOffsetModel>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey ?: "1"
        }
    }
}