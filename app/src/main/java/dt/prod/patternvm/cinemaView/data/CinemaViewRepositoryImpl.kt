package dt.prod.patternvm.cinemaView.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dt.prod.patternvm.core.model.Event
import dt.prod.patternvm.cinemaView.domain.CinemaViewRepository
import dt.prod.patternvm.cinemaView.domain.CinemaViewApi
import dt.prod.patternvm.cinemaView.models.CinemaOffsetModel
import retrofit2.HttpException
import java.io.IOException

class CinemaViewRepositoryImpl(private val createEventApi: CinemaViewApi) : PagingSource<String, CinemaOffsetModel>,
    CinemaViewRepository {

    override suspend fun createEvent(offset: String): Event<List<CinemaOffsetModel>> {
        return try {
            val result = createEventApi.createEvent(offset = offset)
            when (result.status) {
                "OK" -> {
                    Event.success(result.data)
                }
                else -> Event.error(result.error ?: "Ошибка запроса")
            }
        } catch (e: Exception) {
            Event.error(e.message.toString())
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CinemaOffsetModel> {
        return try {
            val items = params.key?.let {
                createEventApi.createEvent(
                    offset = it
                ).data
            }

            LoadResult.Page(
                data = items!!.map { it },
                prevKey = params.key,
                nextKey = (params.key!!.toInt()+20).toString()
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, CinemaOffsetModel>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}