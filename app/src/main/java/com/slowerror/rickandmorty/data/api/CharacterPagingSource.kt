package com.slowerror.rickandmorty.data.api

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import retrofit2.HttpException
import javax.inject.Inject

private const val STARTING_KEY = 1

class CharacterPagingSource @Inject constructor(
    private val api: Api
) : PagingSource<Int, GetCharacterByIdResponse>() {
    override fun getRefreshKey(state: PagingState<Int, GetCharacterByIdResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharacterByIdResponse> {
        return try {
            Log.i("Load was called", "${params.key}")
            val pageNumber = params.key ?: STARTING_KEY
            val response = api.getCharacterList(pageNumber)

            if (response.isSuccessful) {
                val pageResponse = response.body()
                val data = pageResponse?.results
                Log.i("Load was called", "$data")
                val nextPageNumber = getPage(pageResponse?.info?.next)
                val prevPageNumber = getPage(pageResponse?.info?.prev)

                LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
                )

            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Log.i("Load was called", "${e.message}")
            LoadResult.Error(e)

        }
    }

    private fun getPage(page: String?): Int? {
        if (page == null) return null

        val uri = Uri.parse(page)
        val pageQuery = uri.getQueryParameter("page")
        return pageQuery?.toInt()
    }

}