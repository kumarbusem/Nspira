package com.busem.nspira.features.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.busem.data.models.Repository
import com.busem.data.repositories.PagingDataSource
import com.busem.nspira.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel() : BaseViewModel() {

    private val _obsPagingData by lazy { MutableLiveData<PagingData<Repository>>() }
    val obsPagingData: LiveData<PagingData<Repository>> by lazy { _obsPagingData }

    init {
        /**
         * By default it will search for "Kotlin" key
         */
        searchResults("Kotlin")
    }


    fun searchResults(searchTerm: String) {
        Log.e("SEARCH::", searchTerm)
        try {
            ioScope.launch {
                /**
                 * Fetching 15 items for a network call
                 */
                Pager(
                    config = PagingConfig(pageSize = 15, enablePlaceholders = false)
                ) {
                    PagingDataSource(searchTerm)
                }.flow.cachedIn(ioScope).collect {

                    _obsPagingData.postValue(it)

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            obsToastMessage.postValue(e.message)
        }
    }

    fun saveSelectedRepo(repo: Repository) {
        repoPrefs.saveRepo(repo)
    }
}