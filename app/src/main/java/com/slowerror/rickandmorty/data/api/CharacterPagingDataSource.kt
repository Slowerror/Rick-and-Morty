package com.slowerror.rickandmorty.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import retrofit2.HttpException
import javax.inject.Inject

private const val STARTED_KEY = 1

class CharacterPagingDataSource @Inject constructor(
    private val remoteService: RemoteService
) : PagingSource<Int, GetCharacterByIdResponse>() {

    override fun getRefreshKey(state: PagingState<Int, GetCharacterByIdResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharacterByIdResponse> {
        return try {
            val pageNumber = params.key ?: STARTED_KEY
            val response = remoteService.getCharacterList(pageNumber)

            if (response.isSuccessful) {
                val pageResponse = response.body()
                val data = pageResponse?.results

                val prevPageNumber = getPage(pageResponse?.info?.prev)
                val nextPageNumber = getPage(pageResponse?.info?.next)

                LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
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