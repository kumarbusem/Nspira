package com.busem.data.common

import com.busem.data.BuildConfig

// Base HTML_URL for fetching the required data.
internal const val BASE_URL: String = BuildConfig.SERVER_URL
internal const val QUERY_PARAMETER_KEY_SEARCH = "q"
internal const val QUERY_PARAMETER_KEY_PAGE = "page"
internal const val QUERY_PARAMETER_KEY_ITEMS = "per_page"
internal const val EMPTY_STRING = ""
internal var IS_CACHE_ALREADY_LOADED = false