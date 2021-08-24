package com.busem.data.common

import com.busem.data.BuildConfig

// Base HTML_URL for fetching the required data.
const val BASE_URL: String = BuildConfig.SERVER_URL
const val QUERY_PARAMETER_KEY_SEARCH = "q"
const val QUERY_PARAMETER_KEY_PAGE = "page"
const val QUERY_PARAMETER_KEY_ITEMS = "per_page"
const val EMPTY_STRING = ""