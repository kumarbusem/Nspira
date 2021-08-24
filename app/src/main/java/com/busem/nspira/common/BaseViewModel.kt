package com.busem.nspira.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * This class provides a [CoroutineScope] that dispatches on the [Dispatchers.Main] thread
 * and runs a [SupervisorJob] that gets cancelled when the [BaseViewModel] class gets cleared.
 */

abstract class BaseViewModel : ViewModel() {


    @Suppress("PropertyName")
    protected val TAG: String by lazy { javaClass.simpleName }

    val obsIsDataLoading: MutableLiveData<Boolean> = MutableLiveData()

    val obsToastMessage by lazy { SingleLiveEvent<String>() }

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


    protected suspend fun doWhileLoading(logic: suspend () -> Unit) {
        obsIsDataLoading.postValue(true)
        logic()
        obsIsDataLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        // Cancelling all the children of the SupervisorJob at once if the
        // ViewModel gets cleared.
        job.cancel()
    }
}