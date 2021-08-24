package com.busem.nspira.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busem.data.common.DataException
import com.busem.data.common.DataState
import com.busem.data.local.sharedPrefs.SharedPreferencesDataSource
import com.busem.data.repositories.RepoSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * This class provides a [CoroutineScope] that dispatches on the [Dispatchers.Main] thread
 * and runs a [SupervisorJob] that gets cancelled when the [BaseViewModel] class gets cleared.
 */

abstract class BaseViewModel(
    protected val repoPrefs: SharedPreferencesDataSource = RepoSharedPreferences()
) : ViewModel() {


    @Suppress("PropertyName")
    protected val TAG: String by lazy { javaClass.simpleName }

    val obsIsDataLoading: MutableLiveData<Boolean> = MutableLiveData()

    val obsToastMessage by lazy { SingleLiveEvent<String>() }

    var isUserLogout = MutableLiveData<Boolean>()

    /*
    SupervisorJob that handles each task as a separate child.
     */
    private val job = SupervisorJob()

    /**
     * [CoroutineScope] that dispatches the task on the [Dispatchers.Main] thread by default.
     */
    val uiScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * [CoroutineScope] that dispatches the task on the [Dispatchers.IO] thread by default.
     */
    val ioScope = CoroutineScope(job + Dispatchers.IO)

    protected inline fun <reified T> DataState<T>.logDetails(): DataState<T> {
        this.exceptionOrNull()?.let {
            Log.e(TAG, "Failed to fetch response : ${it.message}")
            Log.e(TAG, it.stackTraceToString())
        }
        return this
    }

    protected suspend fun doWhileLoading(logic: suspend () -> Unit) {
        obsIsDataLoading.postValue(true)
        logic()
        obsIsDataLoading.postValue(false)
    }

    internal  fun handleExceptions(dataException: DataException){

        Log.e("ERROR Exception", dataException.printStackTrace().toString())
        when (dataException) {
            is DataException.UnauthorizedException -> logoutUser()
            is DataException.SocketTimeoutException -> obsToastMessage.postValue(dataException.message)
            is DataException.ApiException -> obsToastMessage.postValue(dataException.message)
        }
    }

    internal fun logoutUser() {
        repoPrefs.deleteAllPrefs()
        isUserLogout.postValue(true)
    }


    override fun onCleared() {
        super.onCleared()
        // Cancelling all the children of the SupervisorJob at once if the
        // ViewModel gets cleared.
        job.cancel()
    }
}