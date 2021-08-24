package com.busem.nspira.features.webView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.models.Repository
import com.busem.data.repositories.DataSourceUserRepo
import com.busem.data.repositories.RepoUser
import com.busem.nspira.common.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class WebViewViewModel()  : BaseViewModel() {

    val obsURL = MutableLiveData<String>()

    init {
        obsURL.postValue(repoPrefs.getUrl())
    }

}
