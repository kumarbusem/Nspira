package com.busem.nspira.features.webView

import androidx.lifecycle.MutableLiveData
import com.busem.nspira.common.BaseViewModel


class WebViewViewModel() : BaseViewModel() {

    val obsURL = MutableLiveData<String>()

    init {
        obsURL.postValue(repoPrefs.getUrl())
    }

}
