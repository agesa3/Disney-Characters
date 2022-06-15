package com.agesadev.disney.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.agesadev.disney.data.remote.model.dto.toCharacter
import com.agesadev.disney.data.remote.retrofit.CharacterApi
import com.agesadev.disney.domain.model.Character


private const val CHARACTER_STARTING_INDEX = 1

class CharacterPagingSource(
    private val characterApi: CharacterApi
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchprPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchprPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: CHARACTER_STARTING_INDEX

        return try {
            val response = characterApi.getCharacters(position)
            val result = response.characters.map { it.toCharacter() }
            LoadResult.Page(
                data = result,
                prevKey = if (position == CHARACTER_STARTING_INDEX) null else position - 1,
                nextKey = if (result.isEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}