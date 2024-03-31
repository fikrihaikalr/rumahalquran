package com.fikrihaikal.qurancall.data.paging

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fikrihaikal.qurancall.network.model.response.doa.DataItem
import com.fikrihaikal.qurancall.network.service.ApiService
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.dataStore


class DoaPagingSource(private val apiService: ApiService, private val context:Context)
    : PagingSource<Int, DataItem>(){

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> =
        try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val datastore : DataStore<Preferences> = context.dataStore
            val tokenPreferences : TokenPreferences = TokenPreferences.getInstance(datastore)
            val token = tokenPreferences.getToken().toString()
            val responseData = apiService.getListDoa(token,page).data!!
            LoadResult.Page(
                data = responseData ,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
}

//class DoaPagingSource(private val apiService: ApiService, private val context: Context)
//    : PagingSource<Int, DoaItem>() {
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, DoaItem>): Int? =
//        state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DoaItem> =
//        try {
//            val page = params.key ?: INITIAL_PAGE_INDEX
//            val datastore: DataStore<Preferences> = context.dataStore
//            val tokenPreferences: TokenPreferences = TokenPreferences.getInstance(datastore)
//            val token = tokenPreferences.getToken().toString()
//            val responseData = apiService.getListDoa(token).doa!!
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (responseData.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//}