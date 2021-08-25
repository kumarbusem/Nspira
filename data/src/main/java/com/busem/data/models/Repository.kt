package com.busem.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.busem.data.common.EMPTY_STRING
import com.google.gson.annotations.SerializedName
import kotlin.String

@Entity(tableName = Repository.REPOSITORY)
data class Repository(
    @ColumnInfo(name = ID) @PrimaryKey val id: Int,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = OWNER_IMAGE) val ownerImage: String,
    @ColumnInfo(name = OWNER) val owner: String,
    @ColumnInfo(name = DESCRIPTION) val description: String,
    @ColumnInfo(name = FULL_NAME) val fullName: String,
    @ColumnInfo(name = HAS_DOWNLOADS) val hasDownloads: Boolean?,
    @ColumnInfo(name = HAS_PROJECTS) val hasProjects: Boolean?,
    @ColumnInfo(name = HAS_ISSUES) val hasIssues: Boolean?,
    @ColumnInfo(name = HAS_WIKI) val hasWiki: Boolean?,
    @ColumnInfo(name = WATCHERS_COUNT) val watchersCount: Int,
    @ColumnInfo(name = STAR_COUNT) val starsCount: Int?,
    @ColumnInfo(name = FORK_COUNT) val forksCount: Int?,
    @ColumnInfo(name = HTML_URL) val htmlUrl: String,
    @ColumnInfo(name = COMMITS_URL) val commitsUrl: String,
    @ColumnInfo(name = CONTRIBUTORS_URL) val contributorsUrl: String
) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val OWNER = "owner"
        const val DESCRIPTION = "description"
        const val FULL_NAME = "full_name"
        const val WATCHERS_COUNT = "watchers_count"
        const val HTML_URL = "html_url"
        const val OWNER_IMAGE = "owner_image"
        const val COMMITS_URL = "commits_url"
        const val CONTRIBUTORS_URL = "contributors_url"
        const val HAS_ISSUES = "has_issues"
        const val HAS_PROJECTS = "has_projects"
        const val HAS_DOWNLOADS = "has_downloads"
        const val HAS_WIKI = "has_wiki"
        const val STAR_COUNT = "stargazers_count"
        const val FORK_COUNT = "forks_count"

        const val REPOSITORY = "repository"

        fun mapFromRemoteToLocal(repo: RemoteRepository): Repository {
            return Repository(
                id = repo.id,
                name = repo.name ?: EMPTY_STRING,
                ownerImage = repo.owner?.profilePicUrl ?: EMPTY_STRING,
                owner = repo.owner?.loginName ?: EMPTY_STRING,
                description = repo.description ?: EMPTY_STRING,
                fullName = repo.fullName ?: EMPTY_STRING,
                watchersCount = repo.watchersCount ?: 0,
                htmlUrl = repo.htmlUrl ?: EMPTY_STRING,
                hasDownloads = repo.hasDownloads ?: false,
                hasProjects = repo.hasProjects ?: false,
                hasIssues = repo.hasIssues ?: false,
                hasWiki = repo.hasWiki ?: false,
                commitsUrl = repo.commitsUrl ?: EMPTY_STRING,
                contributorsUrl = repo.contributorsUrl ?: EMPTY_STRING,
                starsCount = repo.starsCount,
                forksCount = repo.forksCount
            )
        }

    }
}