package com.busem.data.remote

import com.busem.data.common.QUERY_PARAMETER_KEY_ITEMS
import com.busem.data.common.QUERY_PARAMETER_KEY_PAGE
import com.busem.data.common.QUERY_PARAMETER_KEY_SEARCH
import com.busem.data.models.Contributor
import com.busem.data.models.RemoteRepository
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Remote service to fetch for the repositories list.
 */
interface NetworkInterface {

    @GET("search/repositories")
    suspend fun fetchRepositories(
        @Query(QUERY_PARAMETER_KEY_SEARCH) q: String,
        @Query(QUERY_PARAMETER_KEY_PAGE) page: Int,
        @Query(QUERY_PARAMETER_KEY_ITEMS) per_page: Int,
    ): Response<RepositoriesResponseBody>

    @GET
    suspend fun fetchContributors(@Url url: String?): Response<List<Contributor>>
}


data class RepositoriesResponseBody(
    @SerializedName(TOTAL_COUNT) val totalCount: Int,
    @SerializedName(INCOMPLETE_RESULTS) val incompleteResults: Boolean,
    @SerializedName(ITEMS) val repositories: List<RemoteRepository>
) {

    companion object {
        const val TOTAL_COUNT = "total_count"
        const val INCOMPLETE_RESULTS = "incomplete_results"
        const val ITEMS = "items"
    }
}