package com.slowerror.rickandmorty.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slowerror.rickandmorty.data.STARTED_KEY
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.getPage
import retrofit2.HttpException
import javax.inject.Inject

class SearchCharacterPagingDataSource @Inject constructor(
    private val remoteService: RemoteService,
    private val userRequest: String
) : PagingSource<Int, GetCharacterByIdResponse>() {

    override fun getRefreshKey(state: PagingState<Int, GetCharacterByIdResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharacterByIdResponse> {
        return try {
            val pageNumber = params.key ?: STARTED_KEY

            val response = remoteService.getCharacterListByName(
                page = pageNumber,
                characterName = userRequest
            )

            if (response.isSuccessful) {
                val pageResponse = response.body()
                val data = response.body()?.results

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


}