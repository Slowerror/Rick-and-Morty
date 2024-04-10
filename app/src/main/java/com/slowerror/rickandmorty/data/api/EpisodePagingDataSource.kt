package com.slowerror.rickandmorty.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slowerror.rickandmorty.data.STARTED_KEY
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import retrofit2.HttpException
import javax.inject.Inject

class EpisodePagingDataSource @Inject constructor(
    private val remoteService: RemoteService
) : PagingSource<Int, GetEpisodeByIdResponse>() {

    override fun getRefreshKey(state: PagingState<Int, GetEpisodeByIdResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetEpisodeByIdResponse> {
        return try {
            val pageNumber = params.key ?: STARTED_KEY
            val response = remoteService.getEpisodeList(pageNumber)

            if (response.isSuccessful) {
                val pageResponse = response.body()
                val data = pageResponse?.result

                val prevKey = getPage(pageResponse?.info?.prev)
                val nextKey = getPage(pageResponse?.info?.next)

                LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(HttpException(response))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getPage(page: String?): Int? {
        if (page == null) return null
        return page.substringAfter("?page=").toInt()
    }
}